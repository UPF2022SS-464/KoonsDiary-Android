package com.upf464.koonsdiary.presentation.model.account

import com.upf464.koonsdiary.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow

internal data class UserEmailModel(
    val usernameFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val emailFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val passwordFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val passwordConfirmFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val imageFlow: MutableStateFlow<User.Image?> = MutableStateFlow(null),
    val nicknameFlow: MutableStateFlow<String> = MutableStateFlow("")
)
