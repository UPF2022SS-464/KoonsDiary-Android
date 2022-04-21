package com.upf464.koonsdiary.presentation.ui.account.signin

sealed class SignInEvent {
    object Success : SignInEvent()
    object Invalid : SignInEvent()
    object NavigateToEmailSignIn : SignInEvent()
    object NavigateToEmailSignUp : SignInEvent()
    object NavigateToKakaoSignUp : SignInEvent()
    object UnknownError : SignInEvent()
}
