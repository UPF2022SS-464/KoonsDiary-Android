package com.upf464.koonsdiary.domain.model

import java.time.LocalDateTime

data class ShareGroup(
    val id: Int = 0,
    val name: String,
    val manager: User = User(username = "", nickname = ""),
    val imagePath: String?,
    val userList: List<User> = emptyList(),
    val createdDate: LocalDateTime = LocalDateTime.now()
)