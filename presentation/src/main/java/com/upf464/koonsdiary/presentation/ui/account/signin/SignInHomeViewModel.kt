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

    private val _eventFlow = MutableSharedFlow<SignInEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    fun signInWithEmail() {
        setEvent(SignInEvent.NavigateToEmailSignIn)
    }

    fun signInWithKakao() {
        viewModelScope.launch {
            kakaoSignInUseCase().onSuccess {
                setEvent(SignInEvent.Success)
            }.onFailure { error ->
                when (error) {
                    SignInError.NoSuchKakaoUser -> setEvent(SignInEvent.NavigateToKakaoSignUp)
                    else -> setEvent(SignInEvent.UnknownError)
                }
            }
        }
    }

    fun signUpWithEmail() {
        setEvent(SignInEvent.NavigateToEmailSignUp)
    }

    private fun setEvent(event: SignInEvent) {
        _eventFlow.tryEmit(event)
    }
}
