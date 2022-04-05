package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.LockTypeData
import com.upf464.koonsdiary.domain.error.LockType

internal fun LockTypeData.toDomain() = when (this) {
    LockTypeData.NONE -> LockType.NONE
    LockTypeData.PIN -> LockType.PIN
    LockTypeData.BIOMETRIC -> LockType.BIOMETRIC
}