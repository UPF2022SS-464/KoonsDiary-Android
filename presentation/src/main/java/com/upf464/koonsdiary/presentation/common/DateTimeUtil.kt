package com.upf464.koonsdiary.presentation.common

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateTimeUtil @Inject constructor() {

    fun formatDateTimeToBefore(dateTime: LocalDateTime): String {
        val now = LocalDateTime.now()
        val secondsDiff = now.toEpochSecond(ZoneOffset.UTC) - dateTime.toEpochSecond(ZoneOffset.UTC)
        return when {
            secondsDiff < 60 -> "방금 전"
            secondsDiff < 60 * 60 -> "${secondsDiff / 60}분 전"
            secondsDiff < 60 * 60 * 24 -> "${secondsDiff / (60 * 60)}시간 전"
            secondsDiff < 60 * 60 * 24 * 7 -> "${secondsDiff / (60 * 60 * 24 * 7)}일 전"
            else -> dateTime.format(dateFormatter)
        }
    }

    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
    }
}
