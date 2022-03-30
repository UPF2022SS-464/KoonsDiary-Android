package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.FetchGroupListRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchGroupListUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: FetchGroupListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchGroupListUseCase(shareRepository)
    }

    @Test
    fun invoke_nothing_isSuccess(): Unit = runBlocking {
        coEvery {
            shareRepository.fetchGroupList()
        } returns Result.success(emptyList())

        val result = useCase(FetchGroupListRequest)

        assertTrue(result.isSuccess)
        assertEquals(emptyList<ShareGroup>(), result.getOrNull()?.groupList)
    }
}