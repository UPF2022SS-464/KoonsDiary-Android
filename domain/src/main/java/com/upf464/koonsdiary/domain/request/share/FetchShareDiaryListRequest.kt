package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class FetchShareDiaryListRequest(
    val groupId: Int
) : Request