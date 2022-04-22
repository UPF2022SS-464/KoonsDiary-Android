package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.MessageData
import com.upf464.koonsdiary.domain.model.Message

internal fun MessageData.toDomain() = Message()
