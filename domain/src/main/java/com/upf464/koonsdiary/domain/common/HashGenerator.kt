package com.upf464.koonsdiary.domain.common

interface HashGenerator {

    fun hashPasswordWithSalt(password: String, salt: String): String
}
