package com.upf464.koonsdiary.presentation.ui.account.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.CommonError
import com.upf464.koonsdiary.domain.usecase.user.FetchUserImageListUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithKakaoUseCase
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import com.upf464.koonsdiary.presentation.mapper.toPresentation
import com.upf464.koonsdiary.presentation.model.account.SignUpPage
import com.upf464.koonsdiary.presentation.model.account.UserImageModel
import com.upf464.koonsdiary.presentation.model.account.UserModel
import com.upf464.koonsdiary.presentation.model.account.UserValidationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class KakaoSignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpWithKakaoUseCase,
    private val fetchImageListUseCase: FetchUserImageListUseCase,
    validateUseCase: ValidateSignUpUseCase
) : ViewModel() {

    private val userModel = UserModel.Kakao()
    private val validationModel = UserValidationModel.Kakao(
        validateUseCase,
        userModel.usernameFlow,
        userModel.imageFlow,
        userModel.nicknameFlow
    )

    private val pageList = listOf(
        SignUpPage.USERNAME,
        SignUpPage.IMAGE,
        SignUpPage.NICKNAME
    )

    private val _pageIndexFlow = MutableStateFlow(0)
    val pageFlow = _pageIndexFlow.map { index ->
        pageList[index]
    }.stateIn(viewModelScope, SharingStarted.Lazily, pageList[0])

    val fieldFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val validationFlow = pageFlow.flatMapLatest { page ->
        when (page) {
            SignUpPage.USERNAME -> validationModel.usernameFlow
            SignUpPage.IMAGE -> validationModel.imageFlow
            SignUpPage.NICKNAME -> validationModel.nicknameFlow
            else -> flow { }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, SignUpValidationState.Waiting)

    private val _imageListFlow = MutableStateFlow(listOf<UserImageModel>())
    val imageListFlow = _imageListFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<SignUpEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            viewModelScope.launch {
                var job: Job? = null
                pageFlow.collect { page ->
                    job?.cancel()
                    val targetFlow = when (page) {
                        SignUpPage.USERNAME -> userModel.usernameFlow
                        SignUpPage.NICKNAME -> userModel.nicknameFlow
                        else -> return@collect
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

        val lastPageIdx = pageList.size - 1
        val currentPageIdx = _pageIndexFlow.value

        if (currentPageIdx == lastPageIdx) {
            signUp()
        } else {
            _pageIndexFlow.value = currentPageIdx + 1
        }
    }

    private fun isNextAvailable(): Boolean {
        return validationFlow.value == SignUpValidationState.Success
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpWithKakaoUseCase.Request(
                    username = userModel.usernameFlow.value,
                    nickname = userModel.nicknameFlow.value,
                    imageId = userModel.imageFlow.value?.id ?: return@launch
                )
            ).onSuccess {
                _eventFlow.tryEmit(SignUpEvent.Success)
            }.onFailure { error ->
                handleError(error)
            }
        }
    }

    fun prevPage() {
        val currentPageIdx = _pageIndexFlow.value
        if (currentPageIdx == 0) return
        _pageIndexFlow.value = currentPageIdx - 1
    }

    private fun handleError(error: Throwable) {
        when (error) {
            CommonError.NetworkDisconnected -> _eventFlow.tryEmit(SignUpEvent.NoNetwork)
            else -> _eventFlow.tryEmit(SignUpEvent.UnknownError)
        }
    }
}
