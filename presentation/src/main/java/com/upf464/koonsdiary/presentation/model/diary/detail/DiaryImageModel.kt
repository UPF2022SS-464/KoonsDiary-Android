package com.upf464.koonsdiary.presentation.model.diary.detail

import kotlinx.coroutines.flow.MutableStateFlow

data class DiaryImageModel(
    val imagePath: String,
    val content: MutableStateFlow<String> = MutableStateFlow("")
)
