package com.upf464.koonsdiary.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DiarySentiment")
data class DiarySentimentDto(
    @PrimaryKey
    @Embedded(prefix = "date_")
    val date: DateDto,
    val sentiment: Int
)
