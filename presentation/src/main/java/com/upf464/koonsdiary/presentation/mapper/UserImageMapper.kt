package com.upf464.koonsdiary.presentation.mapper

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.model.account.UserImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal fun User.Image.toPresentation(
    imageFlow: Flow<UserImageModel?>,
    scope: CoroutineScope
) = UserImageModel(
    id = id,
    path = path,
    selectedFlow = imageFlow.map { it?.id == id }
        .stateIn(scope, SharingStarted.Lazily, false)
)
