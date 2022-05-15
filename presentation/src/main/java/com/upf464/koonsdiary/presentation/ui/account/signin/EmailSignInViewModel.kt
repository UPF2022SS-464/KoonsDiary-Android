package com.upf464.koonsdiary.presentation.ui.account.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.usecase.user.SignInWithAccountUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignInWithKakaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EmailSignInViewModel @Inject constructor(
    private val signInUseCase: SignInWithAccountUseCase,
    private val kakaoSignInUseCase: SignInWithKakaoUseCase
) : ViewModel() {

    val usernameFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")

    private val _eventFlow = MutableSharedFlow<EmailSignInEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    private val _signInState = MutableStateFlow<EmailSignInState>(EmailSignInState.Closed)
    val signInState = _signInState.asStateFlow()

    fun signIn() {
        viewModelScope.launch {
            signInUseCase(
                SignInWithAccountUseCase.Request(
                    username = usernameFlow.value,
                    password = passwordFlow.value
                )
            ).onSuccess {
                _eventFlow.tryEmit(EmailSignInEvent.Success)
            }.onFailure { error ->
                when (error) {
                    SignInError.IncorrectUsernameOrPassword -> _signInState.value = EmailSignInState.Failed
                    else -> {
                        // TODO("오류 처리")
                    }
                }
            }
        }
    }

    fun signInWithKakao() {
        viewModelScope.launch {
            kakaoSignInUseCase().onSuccess {
                _eventFlow.tryEmit(EmailSignInEvent.Success)
            }.onFailure { error ->
                when (error) {
                    SignInError.NoSuchKakaoUser -> _eventFlow.tryEmit(EmailSignInEvent.NavigateToKakaoSignUp)
                    else -> {
                        // TODO("오류 처리")
                    }
                }
            }
        }
    }

    fun signUpWithEmail() {
        _eventFlow.tryEmit(EmailSignInEvent.NavigateToEmailSignUp)
    }
}
