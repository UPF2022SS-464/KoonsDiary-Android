package com.upf464.koonsdiary.presentation.ui.settings

import com.upf464.koonsdiary.domain.model.User

data class SettingsState(
    val userImage: User.Image = User.Image(),
    val nickname: String = "",
    val isEditingNickname: Boolean = false,
    val username: String = "",
    val email: String = "",
    val usePassword: Boolean = false,
    val useBiometric: Boolean = false,
)
