package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class UpdateGroupRequest(
    val groupId: Int,
    val name: String,
    val imagePath: String?
) : Request