package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.SignUpUserData
import com.upf464.koonsdiary.domain.model.SignUpUser

internal fun SignUpUser.toData() = SignUpUserData(
    username = username,
    email = email,
    password = password,
    nickname = nickname
)