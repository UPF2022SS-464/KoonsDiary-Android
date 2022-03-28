package com.upf464.koonsdiary.domain.request.calendar

import com.upf464.koonsdiary.domain.request.Request

data class FetchCalendarRequest(
    val year: Int,
    val month: Int
) : Request