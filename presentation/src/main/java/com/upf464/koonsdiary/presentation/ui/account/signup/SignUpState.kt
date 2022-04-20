package com.upf464.koonsdiary.presentation.ui.account.signup

sealed class SignUpState {
    object None : SignUpState()
    object Success : SignUpState()
    object NoNetwork : SignUpState()
    object Failure : SignUpState()
}
