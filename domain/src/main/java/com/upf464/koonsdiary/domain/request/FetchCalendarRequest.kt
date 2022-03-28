package com.upf464.koonsdiary.domain.request

data class FetchCalendarRequest(
    val year: Int,
    val month: Int
) : Request