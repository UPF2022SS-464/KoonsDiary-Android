package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Question

interface CottonRepository {
    suspend fun fetchRandomQuestion(): Result<Question>
}