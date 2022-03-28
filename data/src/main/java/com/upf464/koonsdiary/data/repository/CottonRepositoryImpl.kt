package com.upf464.koonsdiary.data.repository


import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.CottonRemoteDataSource
import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.repository.CottonRepository
import javax.inject.Inject

internal class CottonRepositoryImpl @Inject constructor(
    private val remote: CottonRemoteDataSource,
): CottonRepository{

    override suspend fun getRandomQuestion(): Result<Question> {
        return remote.getRandomQuestion().map { questiondata ->
            questiondata.toDomain()
        }
    }
}