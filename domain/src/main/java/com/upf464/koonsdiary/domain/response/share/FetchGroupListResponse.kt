package com.upf464.koonsdiary.domain.response.share

import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.response.Response

data class FetchGroupListResponse(
    val groupList: List<ShareGroup>
) : Response