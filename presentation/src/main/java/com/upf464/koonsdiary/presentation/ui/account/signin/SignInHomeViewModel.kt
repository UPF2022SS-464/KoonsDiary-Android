package com.upf464.koonsdiary.presentation.ui.account.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.usecase.user.SignInWithKakaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignInHomeViewModel @Inject constructor(
    private val kakaoSignInUseCase: SignInWithKakaoUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<SignInHomeEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    fun signInWithEmail() {
        _eventFlow.tryEmit(SignInHomeEvent.NavigateToEmailSignIn)
    }

    fun signInWithKakao() {
        viewModelScope.launch {
            kakaoSignInUseCase().onSuccess {
                _eventFlow.tryEmit(SignInHomeEvent.Success)
            }.onFailure { error ->
                when (error) {
                    SignInError.NoSuchKakaoUser -> _eventFlow.tryEmit(SignInHomeEvent.NavigateToKakaoSignUp)
                    else -> _eventFlow.tryEmit(SignInHomeEvent.UnknownError)
                }
            }
        }
    }

    fun signUpWithEmail() {
        _eventFlow.tryEmit(SignInHomeEvent.NavigateToEmailSignUp)
    }
}
