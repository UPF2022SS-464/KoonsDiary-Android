package com.upf464.koonsdiary.data.model

import java.time.LocalDateTime

data class ShareDiaryData(
    val id: Int = 0,
    val group: ShareGroupData,
    val user: UserData = UserData(),
    val content: String,
    val imageList: List<DiaryImageData>,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val lastModifiedDate: LocalDateTime = LocalDateTime.now()
)