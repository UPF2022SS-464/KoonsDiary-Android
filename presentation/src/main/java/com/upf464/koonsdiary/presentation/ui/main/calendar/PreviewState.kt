package com.upf464.koonsdiary.presentation.ui.main.calendar

import com.upf464.koonsdiary.presentation.model.calendar.PreviewModel
import java.time.LocalDate

sealed class PreviewState {

    data class Success(
        val model: PreviewModel
    ) : PreviewState()

    data class Loading(
        val date: LocalDate
    ) : PreviewState()

    data class NoPreview(
        val date: LocalDate
    ) : PreviewState()

    object None : PreviewState()

    data class UnknownError(
        val message: String?
    ) : PreviewState()
}
