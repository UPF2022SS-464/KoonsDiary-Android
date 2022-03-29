package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.domain.model.User

internal fun User.toData() = UserData(
    id = id,
    username = username,
    email = email,
    nickname = nickname
)