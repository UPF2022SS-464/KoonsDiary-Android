package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.model.Reaction

interface CottonRepository {

    suspend fun fetchRandomQuestion(): Result<Question>

    suspend fun addQuestionAnswer(questionAnswer: QuestionAnswer): Result<Int>

    suspend fun fetchRandomAnswerList(): Result<List<QuestionAnswer>>

    suspend fun fetchReactionList(): Result<List<Reaction>>
}
