package com.upf464.koonsdiary.presentation.model.account

import kotlinx.coroutines.flow.MutableStateFlow

sealed class UserModel {

    data class Kakao(
        val usernameFlow: MutableStateFlow<String> = MutableStateFlow(""),
        val imageFlow: MutableStateFlow<UserImageModel?> = MutableStateFlow(null),
        val nicknameFlow: MutableStateFlow<String> = MutableStateFlow("")
    ) : UserModel()

    data class Email(
        val usernameFlow: MutableStateFlow<String> = MutableStateFlow(""),
        val emailFlow: MutableStateFlow<String> = MutableStateFlow(""),
        val passwordFlow: MutableStateFlow<String> = MutableStateFlow(""),
        val passwordConfirmFlow: MutableStateFlow<String> = MutableStateFlow(""),
        val imageFlow: MutableStateFlow<UserImageModel?> = MutableStateFlow(null),
        val nicknameFlow: MutableStateFlow<String> = MutableStateFlow("")
    ) : UserModel()
}
