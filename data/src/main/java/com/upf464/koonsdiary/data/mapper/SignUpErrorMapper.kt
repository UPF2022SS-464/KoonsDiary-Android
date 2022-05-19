package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.error.SignUpErrorData
import com.upf464.koonsdiary.domain.error.SignUpError

internal fun SignUpErrorData.toDomain() = when (this) {
    SignUpErrorData.MissingParameters -> SignUpError.MissingParameters
    is SignUpErrorData.UnknownError -> SignUpError.UnknownError(message)
}
