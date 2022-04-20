package com.upf464.koonsdiary.domain.model

import java.time.LocalDateTime

data class ShareGroup(
    val id: Int = 0,
    val name: String = "",
    val manager: User = User(),
    val imagePath: String? = null,
    val userList: List<User> = emptyList(),
    val createdDate: LocalDateTime = LocalDateTime.now()
)
