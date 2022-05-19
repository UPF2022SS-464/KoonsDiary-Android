package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.error.SecurityErrorData
import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.data.error.SignUpErrorData

internal fun ErrorData.toDomain() = when (this) {
    is SignInErrorData -> toDomain()
    is SecurityErrorData -> toDomain()
    is SignUpErrorData -> toDomain()
}
