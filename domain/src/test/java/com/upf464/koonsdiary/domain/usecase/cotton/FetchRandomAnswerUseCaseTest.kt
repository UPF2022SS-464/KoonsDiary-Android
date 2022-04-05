package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.FetchRandomAnswerRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchRandomAnswerUseCaseTest {

    @MockK
    private lateinit var cottonRepository: CottonRepository
    private lateinit var useCase: FetchRandomAnswerUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchRandomAnswerUseCase(
            cottonRepository = cottonRepository
        )
    }

    @Test
    fun invoke_nothing_isSuccess(): Unit = runBlocking {
        coEvery {
            cottonRepository.fetchRandomAnswer()
        } returns Result.success(emptyList())

        val result = useCase(FetchRandomAnswerRequest)

        assertTrue(result.isSuccess)
        assertEquals(emptyList<QuestionAnswer>(), result.getOrNull()?.questionAnswer)
    }
}