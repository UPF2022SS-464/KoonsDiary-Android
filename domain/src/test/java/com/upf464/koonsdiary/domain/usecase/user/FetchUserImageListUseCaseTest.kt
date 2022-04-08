package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.FetchUserImageListRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchUserImageListUseCaseTest {

    @MockK private lateinit var userRepository: UserRepository
    private lateinit var useCase: FetchUserImageListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchUserImageListUseCase(userRepository)
    }

    @Test
    fun invoke_nothing_returnEmptyList(): Unit = runBlocking {
        coEvery {
            userRepository.fetchUserImageList()
        } returns Result.success(emptyList())

        val result = useCase(FetchUserImageListRequest)

        assertTrue(result.isSuccess)
        assertEquals(emptyList<User.Image>(), result.getOrNull()?.imageList)
    }
}