package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class LeaveGroupRequest(
    val groupId: Int
) : Request