package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class AddGroupRequest(
    val name: String,
    val imagePath: String?,
    val inviteUserIdList: List<Int>
) : Request