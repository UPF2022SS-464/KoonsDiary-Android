package com.upf464.koonsdiary.presentation.ui.main.diary.detail

import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryDetailModel

sealed class DiaryDetailState {

    object Loading : DiaryDetailState()

    data class Success(
        val model: DiaryDetailModel
    ) : DiaryDetailState()

    data class UnknownError(
        val message: String?
    ) : DiaryDetailState()
}
