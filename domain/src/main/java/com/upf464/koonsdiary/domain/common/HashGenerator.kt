package com.upf464.koonsdiary.domain.common

internal interface HashGenerator {

    fun hashPasswordWithSalt(password: String, salt: String): String
}