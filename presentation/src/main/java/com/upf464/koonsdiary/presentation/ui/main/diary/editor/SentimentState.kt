package com.upf464.koonsdiary.presentation.ui.main.diary.editor

import com.upf464.koonsdiary.domain.model.Sentiment

sealed class SentimentState {
    data class Recommended(val sentiment: Sentiment) : SentimentState()
    data class Selected(val sentiment: Sentiment) : SentimentState()
    object InSelect : SentimentState()
    object Closed : SentimentState()
}
