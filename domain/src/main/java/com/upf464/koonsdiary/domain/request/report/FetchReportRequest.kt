package com.upf464.koonsdiary.domain.request.report

import com.upf464.koonsdiary.domain.request.Request

data class FetchReportRequest(
    val period: String,
    val weekly: String,
    val date: String
) : Request