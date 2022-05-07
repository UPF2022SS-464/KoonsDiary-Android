package com.upf464.koonsdiary.presentation.ui.main.diary.add

import com.upf464.koonsdiary.domain.model.Sentiment

sealed class SentimentState {

    data class Recommended(val sentiment: Sentiment) : SentimentState()

    data class Selected(val sentiment: Sentiment) : SentimentState()

    object None : SentimentState()
}
