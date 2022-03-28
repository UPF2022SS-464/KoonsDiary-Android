package com.upf464.koonsdiary.data.repository


import com.upf464.koonsdiary.data.mapper.toDT
import com.upf464.koonsdiary.data.model.QuestionData
import com.upf464.koonsdiary.data.source.CottonRemoteDataSource
import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.response.cotton.GetRandomQuestionResponse
import javax.inject.Inject

internal class CottonRepositoryImpl @Inject constructor(
    private val remote: CottonRemoteDataSource,
): CottonRepository{

    override suspend fun getRandomQuestion(): Result<Question> {
        return remote.getRandomQuestion().map { questiondata ->
            questiondata.toDT()
        }
    }
}