package com.upf464.koonsdiary.domain.request.user

import com.upf464.koonsdiary.domain.request.Request

data class UpdateUserRequest(
    val nickname: String
) : Request