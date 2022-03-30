package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.model.QuestionAnswer

interface CottonRepository {
    suspend fun fetchRandomQuestion(): Result<Question>
    suspend fun addCottonAnswer(questionAnswer: QuestionAnswer): Result<Int>
}