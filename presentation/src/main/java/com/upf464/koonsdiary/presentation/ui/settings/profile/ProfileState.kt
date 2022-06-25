package com.upf464.koonsdiary.presentation.ui.settings.profile

import com.upf464.koonsdiary.domain.model.User

data class ProfileState(
    val isShowing: Boolean = false,
    val imageList: List<User.Image> = listOf(),
    val selectedIndex: Int = -1,
)
