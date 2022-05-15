package com.upf464.koonsdiary.presentation.ui.account.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.CommonError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.usecase.user.FetchUserImageListUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithAccountUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithKakaoUseCase
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.model.account.UserModel
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

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val kakaoSignUpUseCase: SignUpWithKakaoUseCase,
    private val emailSignUpUseCase: SignUpWithAccountUseCase,
    private val fetchImageListUseCase: FetchUserImageListUseCase,
    validateUseCase: ValidateSignUpUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val signUpType = when (savedStateHandle.get<String>(Constants.PARAM_SIGN_UP_TYPE)) {
        SignUpType.KAKAO.name -> SignUpType.KAKAO
        else -> SignUpType.EMAIL
    }

    private val pageList = when (signUpType) {
        SignUpType.KAKAO -> KakaoPageList
        SignUpType.EMAIL -> EmailPageList
    }
    private val _pageIndexFlow = MutableStateFlow(0)
    val pageFlow = _pageIndexFlow.map { index ->
        pageList[index]
    }.stateIn(viewModelScope, SharingStarted.Lazily, pageList[0])

    private val _usernameFlow = MutableStateFlow("")
    private val _imageFlow = MutableStateFlow<User.Image?>(null)
    private val _nicknameFlow = MutableStateFlow("")

    private val _emailFlow = MutableStateFlow("")
    private val _passwordFlow = MutableStateFlow("")
    private val _passwordConfirmFlow = MutableStateFlow("")

    private val userModel = when (signUpType) {
        SignUpType.KAKAO -> UserModel.Kakao(
            validateUseCase = validateUseCase,
            usernameFlow = _usernameFlow.asStateFlow(),
            imageFlow = _imageFlow.asStateFlow(),
            nicknameFlow = _nicknameFlow.asStateFlow()
        )
        SignUpType.EMAIL -> UserModel.Email(
            validateUseCase = validateUseCase,
            emailFlow = _emailFlow.asStateFlow(),
            usernameFlow = _usernameFlow.asStateFlow(),
            passwordFlow = _passwordFlow.asStateFlow(),
            passwordConfirmFlow = _passwordConfirmFlow.asStateFlow(),
            imageFlow = _imageFlow.asStateFlow(),
            nicknameFlow = _nicknameFlow.asStateFlow()
        )
    }

    val imageFlow = _imageFlow.asStateFlow()

    private val _imageListFlow = MutableStateFlow<List<User.Image>>(emptyList())
    val imageListFlow = _imageListFlow.asStateFlow()

    val firstFieldFlow = MutableStateFlow("")
    val firstValidationFlow = pageFlow.flatMapLatest { page ->
        when (page) {
            SignUpPage.EMAIL -> (userModel as UserModel.Email).emailValidationFlow
            SignUpPage.USERNAME -> userModel.usernameValidationFlow
            SignUpPage.PASSWORD -> (userModel as UserModel.Email).passwordValidationFlow
            SignUpPage.IMAGE -> userModel.imageValidationFlow
            SignUpPage.NICKNAME -> userModel.nicknameValidationFlow
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, SignUpValidationState.Waiting)

    val secondFieldFlow = MutableStateFlow("")
    val secondValidationFlow = when (userModel) {
        is UserModel.Email -> userModel.passwordConfirmValidationFlow
        is UserModel.Kakao -> flow {}
    }.stateIn(viewModelScope, SharingStarted.Lazily, SignUpValidationState.Waiting)

    private val _eventFlow = MutableSharedFlow<SignUpEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            var job: Job? = null
            pageFlow.collect { page ->
                job?.cancel()
                val targetFlow = when (page) {
                    SignUpPage.EMAIL -> _emailFlow
                    SignUpPage.USERNAME -> _usernameFlow
                    SignUpPage.PASSWORD -> _passwordFlow
                    SignUpPage.IMAGE -> return@collect
                    SignUpPage.NICKNAME -> _nicknameFlow
                }
                job = connectFieldFlow(
                    targetFlow = targetFlow,
                    fieldFlow = firstFieldFlow,
                    this
                )
            }
        }

        connectFieldFlow(
            targetFlow = _passwordConfirmFlow,
            fieldFlow = secondFieldFlow,
            scope = viewModelScope
        )

        viewModelScope.launch {
            fetchImageListUseCase().onSuccess { response ->
                _imageListFlow.value = response.imageList
                    .also {
                        _imageFlow.value = it.firstOrNull()
                    }
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
        _imageFlow.value = imageListFlow.value[index]
        nextPage()
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
        val firstAvailable = firstValidationFlow.value == SignUpValidationState.Success
        val secondAvailable = pageFlow.value != SignUpPage.PASSWORD || secondValidationFlow.value == SignUpValidationState.Success
        return firstAvailable && secondAvailable
    }

    fun prevPage() {
        val currentPageIdx = _pageIndexFlow.value
        if (currentPageIdx == 0) return
        _pageIndexFlow.value = currentPageIdx - 1
    }

    private fun signUp() {
        viewModelScope.launch {
            when (userModel) {
                is UserModel.Kakao -> {
                    kakaoSignUpUseCase(
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
                is UserModel.Email -> {
                    emailSignUpUseCase(
                        SignUpWithAccountUseCase.Request(
                            email = userModel.emailFlow.value,
                            username = userModel.usernameFlow.value,
                            password = userModel.passwordFlow.value,
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
        }
    }

    private fun handleError(error: Throwable) {
        when (error) {
            CommonError.NetworkDisconnected -> _eventFlow.tryEmit(SignUpEvent.NoNetwork)
            else -> _eventFlow.tryEmit(SignUpEvent.UnknownError)
        }
    }

    companion object {
        private val EmailPageList = listOf(
            SignUpPage.EMAIL,
            SignUpPage.USERNAME,
            SignUpPage.PASSWORD,
            SignUpPage.IMAGE,
            SignUpPage.NICKNAME
        )
        private val KakaoPageList = listOf(
            SignUpPage.USERNAME,
            SignUpPage.IMAGE,
            SignUpPage.NICKNAME
        )
    }
}
