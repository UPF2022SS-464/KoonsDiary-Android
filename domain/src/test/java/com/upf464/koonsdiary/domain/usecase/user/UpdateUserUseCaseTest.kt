package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.UserValidator
import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateUserUseCaseTest {

    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var validator: UserValidator
    private lateinit var useCase: UpdateUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = UpdateUserUseCase(
            userRepository = userRepository,
            userValidator = validator
        )
    }

    @Test
    fun invoke_validNickname_isSuccess(): Unit = runBlocking {
        every {
            validator.isNicknameValid("nickname")
        } returns true

        coEvery {
            userRepository.updateUser("nickname")
        } returns Result.success(Unit)

        val result = useCase(UpdateUserUseCase.Request("nickname"))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidNickname_throwsInvalidNicknameError(): Unit = runBlocking {
        every {
            validator.isNicknameValid("nickname")
        } returns false

        val result = useCase(UpdateUserUseCase.Request("nickname"))

        assertTrue(result.isFailure)
        assertEquals(SignUpError.InvalidNickname, result.exceptionOrNull())
    }
}
