package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.repository.CottonRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchRandomAnswerListUseCaseTest {

    @MockK
    private lateinit var cottonRepository: CottonRepository
    private lateinit var useCase: FetchRandomAnswerListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchRandomAnswerListUseCase(
            cottonRepository = cottonRepository
        )
    }

    @Test
    fun invoke_nothing_isSuccess(): Unit = runBlocking {
        coEvery {
            cottonRepository.fetchRandomAnswerList()
        } returns Result.success(emptyList())

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(emptyList<QuestionAnswer>(), result.getOrNull()?.questionAnswerList)
    }
}
