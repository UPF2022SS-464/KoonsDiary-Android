package com.upf464.koonsdiary.presentation.ui.main.diary

import com.upf464.koonsdiary.presentation.model.NavigationRoute

enum class DiaryNavigation(override val route: String): NavigationRoute {
    CALENDAR("calendar"),
    DETAIL("detail"),
    EDITOR("editor")
}
