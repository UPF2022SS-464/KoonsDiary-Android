package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.QuestionAnswerData
import com.upf464.koonsdiary.data.model.QuestionData
import com.upf464.koonsdiary.data.source.CottonRemoteDataSource
import javax.inject.Inject

internal class CottonRemoteDevDataSourceImpl @Inject constructor(
): CottonRemoteDataSource {

    override suspend fun fetchRandomQuestion(): Result<QuestionData> {
        return Result.success(QuestionData(id = 1, korean = "", english = ""))
    }

    override suspend fun addQuestionAnswer(questionAnswer: QuestionAnswerData): Result<Int> {
        return Result.success(0)
    }

    override suspend fun fetchRandomAnswer(answerId: Int): Result<QuestionAnswerData> {
        return Result.success(
            QuestionAnswerData(
                id = 1,
                writerId = 1,
                content = "content",
                questionId = QuestionData(id = 1, korean = "", english = "")
            )
        )
    }
}