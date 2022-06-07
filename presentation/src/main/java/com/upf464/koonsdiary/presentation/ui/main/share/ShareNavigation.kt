package com.upf464.koonsdiary.presentation.ui.main.share

import com.upf464.koonsdiary.presentation.model.NavigationRoute

enum class ShareNavigation(override val route: String) : NavigationRoute {
    GROUP_LIST("groupList"),
    GROUP_DETAIL("groupDetail"),
    ADD_GROUP("addGroup")
}
