package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class InviteUserRequest(
    val groupId: Int,
    val userIdList: List<Int>
) : Request