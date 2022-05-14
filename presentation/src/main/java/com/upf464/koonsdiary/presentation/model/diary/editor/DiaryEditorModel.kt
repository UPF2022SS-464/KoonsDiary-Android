package com.upf464.koonsdiary.presentation.model.diary.editor

import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryImageModel
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

data class DiaryEditorModel(
    val id: Int?,
    val dateFlow: StateFlow<LocalDate>,
    val imageListFlow: StateFlow<List<DiaryImageModel>>,
    val contentFlow: StateFlow<String>,
    var sentiment: Sentiment?
)
