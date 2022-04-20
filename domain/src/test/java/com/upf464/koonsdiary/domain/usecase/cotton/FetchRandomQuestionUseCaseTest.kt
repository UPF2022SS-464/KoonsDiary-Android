package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.repository.CottonRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchRandomQuestionUseCaseTest {

    @MockK
    private lateinit var cottonRepository: CottonRepository
    private lateinit var useCase: FetchRandomQuestionUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchRandomQuestionUseCase(
            cottonRepository = cottonRepository
        )
    }

    @Test
    fun invoke_nothing_isSuccess(): Unit = runBlocking {
        val fetchedQuestion = Question(id = 1, korean = "안녕", english = "hi")

        coEvery {
            cottonRepository.fetchRandomQuestion()
        } returns Result.success(fetchedQuestion)

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(fetchedQuestion, result.getOrNull()?.question)
    }
}
