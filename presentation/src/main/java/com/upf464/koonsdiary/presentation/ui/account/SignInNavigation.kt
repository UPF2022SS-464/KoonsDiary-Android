package com.upf464.koonsdiary.presentation.ui.account

import com.upf464.koonsdiary.presentation.model.NavigationRoute

enum class SignInNavigation(override val route: String) : NavigationRoute {
    SIGN_IN_HOME("sign_in"),
    SIGN_IN_EMAIL("sign_in/email"),
    SIGN_UP("sign_up")
}
