package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.ShareRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchUserUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: SearchUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SearchUserUseCase(shareRepository)
    }

    @Test
    fun invoke_validKeyword_returnsEmptyList(): Unit = runBlocking {
        coEvery {
            shareRepository.searchUser("keyword")
        } returns Result.success(emptyList())

        val result = useCase(SearchUserUseCase.Request("keyword"))

        assertTrue(result.isSuccess)
        assertEquals(emptyList<User>(), result.getOrNull()?.userList)
    }

    @Test
    fun invoke_emptyKeyword_throwsEmptyContentError(): Unit = runBlocking {

        val result = useCase(SearchUserUseCase.Request(""))

        assertTrue(result.isFailure)
        assertEquals(ShareError.EmptyContent, result.exceptionOrNull())
    }
}
