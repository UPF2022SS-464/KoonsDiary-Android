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

    sealed class SignInEvent {

        object KakaoSignInSuccess : SignInEvent()

        object KakaoSignUp : SignInEvent()

        object EmailSignIn : SignInEvent()

        object EmailSignUp : SignInEvent()

        object UnknownError : SignInEvent()
    }

    fun signInWithEmail() {
        setEvent(SignInEvent.EmailSignIn)
    }

    fun signInWithKakao() {
        viewModelScope.launch {
            kakaoSignInUseCase().onSuccess {
                setEvent(SignInEvent.KakaoSignInSuccess)
            }.onFailure { error ->
                when (error) {
                    SignInError.NoSuchKakaoUser -> setEvent(SignInEvent.KakaoSignUp)
                    else -> setEvent(SignInEvent.UnknownError)
                }
            }
        }
    }

    fun signUpWithEmail() {
        setEvent(SignInEvent.EmailSignUp)
    }

    private fun setEvent(event: SignInEvent) {
        _eventFlow.tryEmit(event)
    }
}
