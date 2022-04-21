package com.upf464.koonsdiary.presentation.ui.account.signup

sealed class SignUpEvent {
    object Success : SignUpEvent()
    object NoNetwork : SignUpEvent()
    object UnknownError : SignUpEvent()
}
