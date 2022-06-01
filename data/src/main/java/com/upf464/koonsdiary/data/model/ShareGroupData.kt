package com.upf464.koonsdiary.data.model

import java.time.LocalDateTime

data class ShareGroupData(
    val id: Int = 0,
    val name: String = "",
    val manager: UserData = UserData(username = "", nickname = ""),
    val imagePath: String? = null,
    val userList: List<UserData> = emptyList(),
    val createdDate: LocalDateTime = LocalDateTime.now()
)
