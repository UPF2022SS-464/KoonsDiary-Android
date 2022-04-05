package com.upf464.koonsdiary.domain.response.security

import com.upf464.koonsdiary.domain.error.LockType
import com.upf464.koonsdiary.domain.response.Response

data class FetchLockTypeResponse(
    val type: LockType
) : Response