package com.upf464.koonsdiary.data.model

data class UserData(
    val id: Int = 0,
    val username: String = "",
    val email: String? = null,
    val nickname: String = "",
    val image: Image = Image()
) {

    data class Image(
        val id: Int = 0,
        val path: String = ""
    )
}
