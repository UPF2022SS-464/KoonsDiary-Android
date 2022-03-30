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
}