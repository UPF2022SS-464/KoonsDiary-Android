package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.domain.model.User

internal fun User.toData() = UserData(
    id = id,
    username = username,
    email = email,
    nickname = nickname,
    image = image.toData()
)

internal fun UserData.toDomain() = User(
    id = id,
    username = username,
    email = email,
    nickname = nickname,
    image = image.toDomain()
)

internal fun User.Image.toData() = UserData.Image(
    id = id,
    path = path
)

internal fun UserData.Image.toDomain() = User.Image(
    id = id,
    path = path
)
