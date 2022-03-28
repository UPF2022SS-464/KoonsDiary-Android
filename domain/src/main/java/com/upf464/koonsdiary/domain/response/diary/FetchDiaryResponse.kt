package com.upf464.koonsdiary.domain.response.diary

import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.response.Response

data class FetchDiaryResponse(
    val diary: Diary
) : Response