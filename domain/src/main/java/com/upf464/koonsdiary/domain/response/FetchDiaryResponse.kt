package com.upf464.koonsdiary.domain.response

import com.upf464.koonsdiary.domain.model.Diary

data class FetchDiaryResponse(
    val diary: Diary
) : Response