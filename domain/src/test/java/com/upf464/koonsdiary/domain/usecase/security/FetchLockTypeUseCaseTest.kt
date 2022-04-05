package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.error.LockType
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.request.security.FetchLockTypeRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchLockTypeUseCaseTest {

    @MockK private lateinit var securityRepository: SecurityRepository
    private lateinit var useCase: FetchLockTypeUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchLockTypeUseCase(securityRepository)
    }

    @Test
    fun invoke_nothing_returnNone(): Unit = runBlocking {
        coEvery {
            securityRepository.fetchLockType()
        } returns Result.success(LockType.NONE)

        val result = useCase(FetchLockTypeRequest)

        assertTrue(result.isSuccess)
    }
}