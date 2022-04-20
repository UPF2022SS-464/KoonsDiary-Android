package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.ShareGroupData
import com.upf464.koonsdiary.domain.model.ShareGroup

internal fun ShareGroup.toData() = ShareGroupData(
    id = id,
    name = name,
    manager = manager.toData(),
    imagePath = imagePath,
    userList = userList.map { it.toData() }
)

internal fun ShareGroupData.toDomain() = ShareGroup(
    id = id,
    name = name,
    manager = manager.toDomain(),
    imagePath = imagePath,
    userList = userList.map { it.toDomain() }
)
