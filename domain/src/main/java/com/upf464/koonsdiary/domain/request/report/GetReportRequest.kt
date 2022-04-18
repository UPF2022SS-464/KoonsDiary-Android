package com.upf464.koonsdiary.domain.request.report

import com.upf464.koonsdiary.domain.model.DateTerm
import com.upf464.koonsdiary.domain.request.Request
import java.time.LocalDate

data class GetReportRequest(
    val dateTerm: DateTerm,
    val startDate: LocalDate,
) : Request