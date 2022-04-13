package com.upf464.koonsdiary.presentation.ui.account.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.CommonError
import com.upf464.koonsdiary.domain.request.user.FetchUserImageListRequest
import com.upf464.koonsdiary.domain.request.user.SignUpWithUsernameRequest
import com.upf464.koonsdiary.domain.request.user.ValidateSignUpRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.response.user.FetchUserImageListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.presentation.mapper.toPresentation
import com.upf464.koonsdiary.presentation.model.account.UserEmailModel
import com.upf464.koonsdiary.presentation.model.account.UserImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EmailSignUpViewModel @Inject constructor(
    private val signUpUseCase: ResultUseCase<SignUpWithUsernameRequest, EmptyResponse>,
    private val fetchImageListUseCase: ResultUseCase<FetchUserImageListRequest, FetchUserImageListResponse>,
    validateUseCase: ResultUseCase<ValidateSignUpRequest, EmptyResponse>
) : ViewModel() {

    private val userModel = UserEmailModel(validateUseCase)
    private val _pageFlow = MutableStateFlow(SignUpPage.EMAIL)
    val pageFlow = _pageFlow.asStateFlow()

    enum class SignUpPage {
        EMAIL,
        USERNAME,
        PASSWORD,
        IMAGE,
        NICKNAME
    }

    val firstFieldFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val firstValidationFlow = pageFlow.flatMapLatest { page ->
        when (page) {
            SignUpPage.EMAIL -> userModel.emailValidFlow
            SignUpPage.USERNAME -> userModel.usernameValidFlow
            SignUpPage.PASSWORD -> userModel.passwordValidFlow
            SignUpPage.IMAGE -> userModel.imageValidFlow
            SignUpPage.NICKNAME -> userModel.nicknameValidFlow
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UserEmailModel.State.WAITING)

    val secondFieldFlow = MutableStateFlow("")
    val secondValidationFlow = userModel.passwordConfirmValidFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, UserEmailModel.State.WAITING)

    private val _imageListFlow = MutableStateFlow(listOf<UserImageModel>())
    val imageListFlow = _imageListFlow.asStateFlow()

    private val imageIndexFlow = MutableStateFlow(0)

    private val _eventFlow = MutableSharedFlow<SignUpEvent>(extraBufferCapacity = 1)
    val eventFlow: Flow<SignUpEvent> = _eventFlow

    sealed class SignUpEvent {

        object NoImageSelected : SignUpEvent()

        object Success : SignUpEvent()

        object NetworkDisconnected : SignUpEvent()

        object UnknownError : SignUpEvent()
    }

    init {
        viewModelScope.launch {
            var job: Job? = null
            pageFlow.collect { page ->
                job?.cancel()
                val targetFlow = when (page) {
                    SignUpPage.EMAIL -> userModel.emailFlow
                    SignUpPage.USERNAME -> userModel.usernameFlow
                    SignUpPage.PASSWORD -> userModel.passwordFlow
                    SignUpPage.NICKNAME -> userModel.nicknameFlow
                    SignUpPage.IMAGE -> return@collect
                }
                job = connectFieldFlow(targetFlow, firstFieldFlow, this)
            }
        }

        connectFieldFlow(userModel.passwordConfirmFlow, secondFieldFlow, viewModelScope)

        viewModelScope.launch {
            combine(imageListFlow, imageIndexFlow) { imageList, index ->
                imageList.mapIndexed { i, model ->
                    model.selectedFlow.value = i == index
                }

                userModel.imageFlow.value = imageList.getOrNull(index)
            }.collect()
        }

        viewModelScope.launch {
            fetchImageListUseCase(FetchUserImageListRequest).onSuccess { response ->
                _imageListFlow.value = response.imageList.map { it.toPresentation() }
            }.onFailure { error ->
                handleError(error)
            }
        }
    }

    private fun connectFieldFlow(
        targetFlow: MutableStateFlow<String>,
        fieldFlow: MutableStateFlow<String>,
        scope: CoroutineScope
    ): Job {
        fieldFlow.value = targetFlow.value
        return scope.launch {
            fieldFlow.collect {
                targetFlow.value = it
            }
        }
    }

    fun selectImageAt(index: Int) {
        imageIndexFlow.value = index
    }

    fun nextPage() {
        if (!isNextAvailable()) return

        val lastPageIdx = SignUpPage.values().size - 1
        val currentPageIdx = _pageFlow.value.ordinal

        if (currentPageIdx == lastPageIdx) {
            signUp()
        } else {
            _pageFlow.value = SignUpPage.values()[currentPageIdx + 1]
        }
    }

    private fun isNextAvailable(): Boolean {
        return firstValidationFlow.value == UserEmailModel.State.SUCCESS &&
                (pageFlow.value != SignUpPage.PASSWORD ||
                        secondValidationFlow.value == UserEmailModel.State.SUCCESS)
    }

    fun prevPage() {
        val currentPageIdx = _pageFlow.value.ordinal
        if (currentPageIdx == 0) return
        _pageFlow.value = SignUpPage.values()[currentPageIdx - 1]
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpWithUsernameRequest(
                    email = userModel.emailFlow.value,
                    username = userModel.usernameFlow.value,
                    password = userModel.passwordFlow.value,
                    nickname = userModel.nicknameFlow.value,
                    imageId = userModel.imageFlow.value?.id ?: run {
                        setEvent(SignUpEvent.NoImageSelected)
                        return@launch
                    }
                )
            ).onSuccess {
                setEvent(SignUpEvent.Success)
            }.onFailure { error ->
                handleError(error)
            }
        }
    }

    private fun handleError(error: Throwable) {
        when (error) {
            CommonError.NetworkDisconnected -> setEvent(SignUpEvent.NetworkDisconnected)
            else -> setEvent(SignUpEvent.UnknownError)
        }
    }

    private fun setEvent(event: SignUpEvent) {
        _eventFlow.tryEmit(event)
    }
}
