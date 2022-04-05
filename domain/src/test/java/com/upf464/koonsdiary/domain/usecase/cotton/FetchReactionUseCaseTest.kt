package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.model.Reaction
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.FetchReactionRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchReactionUseCaseTest {

    @MockK
    private lateinit var cottonRepository: CottonRepository
    private lateinit var useCase: FetchReactionUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchReactionUseCase(
            cottonRepository = cottonRepository
        )
    }

    @Test
    fun invoke_nothing_isSuccess(): Unit = runBlocking {
        val fetchedReaction = Reaction(id = 1, name = "이름")

        coEvery {
            cottonRepository.fetchReaction()
        } returns Result.success(fetchedReaction)

        val result = useCase(FetchReactionRequest)

        assertTrue(result.isSuccess)
        assertEquals(fetchedReaction, result.getOrNull()?.reaction)
    }
}