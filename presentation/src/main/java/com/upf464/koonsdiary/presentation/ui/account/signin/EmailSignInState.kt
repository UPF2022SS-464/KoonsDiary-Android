package com.upf464.koonsdiary.presentation.ui.account.signin

sealed class EmailSignInState {
    object Closed : EmailSignInState()
    object Failed : EmailSignInState()
}
