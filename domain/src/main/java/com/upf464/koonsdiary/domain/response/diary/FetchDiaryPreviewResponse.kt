package com.upf464.koonsdiary.domain.response.diary

import com.upf464.koonsdiary.domain.model.DiaryPreview
import com.upf464.koonsdiary.domain.response.Response

data class FetchDiaryPreviewResponse(
    val preview: DiaryPreview
) : Response