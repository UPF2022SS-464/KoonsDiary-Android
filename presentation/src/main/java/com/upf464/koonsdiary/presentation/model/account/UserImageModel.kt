package com.upf464.koonsdiary.presentation.model.account

import kotlinx.coroutines.flow.MutableStateFlow

data class UserImageModel(
    val id: Int,
    val path: String,
    val selectedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
)
