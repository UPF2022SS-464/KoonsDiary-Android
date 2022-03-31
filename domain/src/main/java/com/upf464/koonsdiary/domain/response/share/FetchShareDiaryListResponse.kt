package com.upf464.koonsdiary.domain.response.share

import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.response.Response

data class FetchShareDiaryListResponse(
    val diaryList: List<ShareDiary>
) : Response