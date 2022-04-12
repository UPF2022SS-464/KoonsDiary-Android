package com.upf464.koonsdiary.presentation.mapper

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.model.account.UserImageModel

internal fun User.Image.toPresentation() = UserImageModel(
    id = id,
    path = path
)
