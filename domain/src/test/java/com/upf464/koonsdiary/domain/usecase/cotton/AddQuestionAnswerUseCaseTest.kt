package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.error.CottonError
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.AddQuestionAnswerRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddQuestionAnswerUseCaseTest {

    @MockK
    private lateinit var cottonRepository: CottonRepository
    private lateinit var useCase: AddQuestionAnswerUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = AddQuestionAnswerUseCase(
            cottonRepository = cottonRepository
        )
    }

    @Test
    fun invoke_validQuestionAnswer_isSuccess(): Unit = runBlocking {
        coEvery {
            cottonRepository.addQuestionAnswer(any())
        } returns Result.success(1)

        val result = useCase(
            AddQuestionAnswerRequest(
                questionId = 1,
                content = "content"
            )
        )

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.answerId)
    }

    @Test
    fun invoke_emptyQuestionAnswer_isFailure(): Unit = runBlocking {

        val result = useCase(
            AddQuestionAnswerRequest(
                questionId = 1,
                content = ""
            )
        )

        assertTrue(result.isFailure)
        assertEquals(CottonError.EmptyContent, result.exceptionOrNull())
    }
}