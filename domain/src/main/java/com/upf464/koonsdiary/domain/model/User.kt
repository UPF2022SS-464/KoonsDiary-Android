package com.upf464.koonsdiary.domain.model

data class User(
    val id: Int = 0,
    val username: String = "",
    val email: String? = null,
    val nickname: String = ""
) {

    data class Image(
        val id: Int,
        val path: String
    )
}
