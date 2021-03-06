package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.CottonRemoteDataSource
import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.model.Reaction
import com.upf464.koonsdiary.domain.repository.CottonRepository
import javax.inject.Inject

internal class CottonRepositoryImpl @Inject constructor(
    private val remote: CottonRemoteDataSource,
) : CottonRepository {

    override suspend fun fetchRandomQuestion(): Result<Question> {
        return remote.fetchRandomQuestion().map { questionData ->
            questionData.toDomain()
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun addQuestionAnswer(questionAnswer: QuestionAnswer): Result<Int> {
        return remote.addQuestionAnswer(questionAnswer.toData()).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun fetchRandomAnswerList(): Result<List<QuestionAnswer>> {
        return remote.fetchRandomAnswerList().map { questionAnswerList ->
            questionAnswerList.map { it.toDomain() }
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun fetchReactionList(): Result<List<Reaction>> {
        return remote.fetchReactionList().map { reactionList ->
            reactionList.map { it.toDomain() }
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}
