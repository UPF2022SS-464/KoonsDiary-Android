package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.QuestionData

interface CottonRemoteDataSource {

    suspend fun getRandomQuestion(): Result<QuestionData>
}