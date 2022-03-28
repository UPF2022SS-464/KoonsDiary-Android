package com.upf464.koonsdiary.domain.response.calendar

import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.response.Response

data class FetchCalendarResponse(
    val sentimentList: List<Sentiment?>
) : Response