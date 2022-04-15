package com.upf464.koonsdiary.presentation.ui.account.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.CommonError
import com.upf464.koonsdiary.domain.usecase.user.FetchUserImageListUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithKakaoUseCase
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import com.upf464.koonsdiary.presentation.mapper.toPresentation
import com.upf464.koonsdiary.presentation.model.account.SignUpState
import com.upf464.koonsdiary.presentation.model.account.UserImageModel
import com.upf464.koonsdiary.presentation.model.account.UserKakaoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class KakaoSignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpWithKakaoUseCase,
    private val fetchImageListUseCase: FetchUserImageListUseCase,
    validateUseCase: ValidateSignUpUseCase
) : ViewModel() {

    private val userModel = UserKakaoModel(validateUseCase)
    private val _pageFlow = MutableStateFlow(KakaoSignUpPage.USERNAME)
    val pageFlow = _pageFlow.asStateFlow()

    enum class KakaoSignUpPage {
        USERNAME,
        IMAGE,
        NICKNAME
    }

    val fieldFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val validationFlow = pageFlow.flatMapLatest { page ->
        when (page) {
            KakaoSignUpPage.USERNAME -> userModel.usernameValidFlow
            KakaoSignUpPage.IMAGE -> userModel.imageValidFlow
            KakaoSignUpPage.NICKNAME -> userModel.nicknameValidFlow
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, SignUpState.WAITING)

    private val _imageListFlow = MutableStateFlow(listOf<UserImageModel>())
    val imageListFlow = _imageListFlow.asStateFlow()

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
            viewModelScope.launch {
                var job: Job? = null
                pageFlow.collect { page ->
                    job?.cancel()
                    val targetFlow = when (page) {
                        KakaoSignUpPage.USERNAME -> userModel.usernameFlow
                        KakaoSignUpPage.NICKNAME -> userModel.nicknameFlow
                        KakaoSignUpPage.IMAGE -> return@collect
                    }
                    job = connectFieldFlow(targetFlow, fieldFlow, this)
                }
            }

            viewModelScope.launch {
                fetchImageListUseCase().onSuccess { response ->
                    _imageListFlow.value = response.imageList
                        .map { it.toPresentation(userModel.imageFlow, this) }
                        .also {
                            userModel.imageFlow.value = it.firstOrNull()
                        }
                }.onFailure { error ->
                    handleError(error)
                }
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
        userModel.imageFlow.value = imageListFlow.value[index]
    }

    fun nextPage() {
        if (!isNextAvailable()) return

        val lastPageIdx = KakaoSignUpPage.values().size - 1
        val currentPageIdx = _pageFlow.value.ordinal

        if (currentPageIdx == lastPageIdx) {
            signUp()
        } else {
            _pageFlow.value = KakaoSignUpPage.values()[currentPageIdx + 1]
        }
    }

    private fun isNextAvailable(): Boolean {
        return validationFlow.value == SignUpState.SUCCESS
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpWithKakaoUseCase.Request(
                    username = userModel.usernameFlow.value,
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

    fun prevPage() {
        val currentPageIdx = _pageFlow.value.ordinal
        if (currentPageIdx == 0) return
        _pageFlow.value = KakaoSignUpPage.values()[currentPageIdx - 1]
    }

    private fun setEvent(event: SignUpEvent) {
        _eventFlow.tryEmit(event)
    }
}
