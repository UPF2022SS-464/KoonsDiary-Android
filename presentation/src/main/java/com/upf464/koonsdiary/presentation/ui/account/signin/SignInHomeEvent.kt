package com.upf464.koonsdiary.presentation.ui.account.signin

sealed class SignInHomeEvent {
    object Success : SignInHomeEvent()
    object NavigateToEmailSignIn : SignInHomeEvent()
    object NavigateToEmailSignUp : SignInHomeEvent()
    object NavigateToKakaoSignUp : SignInHomeEvent()
    object UnknownError : SignInHomeEvent()
}
