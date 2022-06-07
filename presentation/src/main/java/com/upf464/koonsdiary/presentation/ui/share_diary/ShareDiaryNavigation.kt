package com.upf464.koonsdiary.presentation.ui.share_diary

import com.upf464.koonsdiary.presentation.model.NavigationRoute

enum class ShareDiaryNavigation(override val route: String) : NavigationRoute {
    DIARY_DETAIL("diaryDetail"),
    EDITOR("editor")
}
