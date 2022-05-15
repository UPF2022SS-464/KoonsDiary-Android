package com.upf464.koonsdiary.presentation.ui.account.signin

sealed class EmailSignInEvent {
    object Success : EmailSignInEvent()
    object NavigateToKakaoSignUp : EmailSignInEvent()
    object NavigateToEmailSignUp : EmailSignInEvent()
}
