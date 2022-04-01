package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class KickUserRequest(
    val groupId: Int,
    val userId: Int
) : Request