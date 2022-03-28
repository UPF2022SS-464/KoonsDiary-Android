package com.upf464.koonsdiary.domain.request.diary

import com.upf464.koonsdiary.domain.request.Request

data class DeleteDiaryRequest(
    val diaryId: Int
) : Request