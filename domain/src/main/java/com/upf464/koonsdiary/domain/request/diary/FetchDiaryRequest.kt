package com.upf464.koonsdiary.domain.request.diary

import com.upf464.koonsdiary.domain.request.Request

data class FetchDiaryRequest(
    val diaryId: Int
) : Request