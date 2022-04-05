package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.error.CottonError
import com.upf464.koonsdiary.domain.model.Question
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
    fun invoke_validAnswerId_isSuccess(): Unit = runBlocking {
        val fetchedAnswer = QuestionAnswer(
            id = 1,
            writerId = 1,
            content = "content",
            questionId = Question(id = 1, korean = "안녕", english = "hi")
        )

        coEvery {
            cottonRepository.fetchRandomAnswer(1)
        } returns Result.success(fetchedAnswer)

        val result = useCase(FetchRandomAnswerRequest(1))

        assertTrue(result.isSuccess)
        assertEquals(fetchedAnswer, result.getOrNull()?.questionAnswer)
    }

    @Test
    fun invoke_invalidAnswerId_isFailure(): Unit = runBlocking {
        coEvery {
            cottonRepository.fetchRandomAnswer(1)
        } returns Result.failure(CottonError.InvalidAnswerId)

        val result = useCase(FetchRandomAnswerRequest(1))

        assertTrue(result.isFailure)
        assertEquals(CottonError.InvalidAnswerId, result.exceptionOrNull())
    }
}