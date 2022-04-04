package com.upf464.koonsdiary.domain.common

internal interface UserValidator {

    fun isEmailValid(email: String): Boolean

    fun isPasswordValid(password: String): Boolean

    fun isNicknameValid(nickname: String): Boolean

    fun isUsernameValid(username: String): Boolean
}