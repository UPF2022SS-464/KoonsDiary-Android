package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.model.Reaction
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.FetchReactionListRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchReactionListUseCaseTest {

    @MockK
    private lateinit var cottonRepository: CottonRepository
    private lateinit var useCase: FetchReactionListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchReactionListUseCase(
            cottonRepository = cottonRepository
        )
    }

    @Test
    fun invoke_nothing_isSuccess(): Unit = runBlocking {
        coEvery {
            cottonRepository.fetchReactionList()
        } returns Result.success(emptyList())

        val result = useCase(FetchReactionListRequest)

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Reaction>(), result.getOrNull()?.reactionList)
    }
}