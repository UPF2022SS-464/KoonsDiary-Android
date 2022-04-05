package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.QuestionAnswerData
import com.upf464.koonsdiary.data.model.QuestionData
import com.upf464.koonsdiary.data.model.ReactionData

interface CottonRemoteDataSource {

    suspend fun fetchRandomQuestion(): Result<QuestionData>

    suspend fun addQuestionAnswer(questionAnswer: QuestionAnswerData): Result<Int>

    suspend fun fetchRandomAnswer(answerId: Int): Result<QuestionAnswerData>

    suspend fun fetchReaction(): Result<ReactionData>
}