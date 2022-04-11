package com.upf464.koonsdiary.domain.request.user

import com.upf464.koonsdiary.domain.request.Request

data class ValidateSignUpRequest(
    val type: Type,
    val content: String
) : Request {

    enum class Type {
        EMAIL,
        USERNAME,
        PASSWORD,
        NICKNAME
    }
}
