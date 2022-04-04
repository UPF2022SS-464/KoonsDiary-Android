package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.error.SecurityErrorData
import com.upf464.koonsdiary.domain.error.SecurityError

internal fun SecurityErrorData.toDomain() = when (this) {
    SecurityErrorData.NoStoredPIN -> SecurityError.NoStoredPIN(message, this)
}