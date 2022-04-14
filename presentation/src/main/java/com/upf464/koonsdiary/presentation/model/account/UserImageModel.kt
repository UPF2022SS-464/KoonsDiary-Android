package com.upf464.koonsdiary.presentation.model.account

import kotlinx.coroutines.flow.StateFlow

data class UserImageModel(
    val id: Int,
    val path: String,
    val selectedFlow: StateFlow<Boolean>
)
