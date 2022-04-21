package com.upf464.koonsdiary.presentation.ui.account.signup

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.usecase.user.FetchUserImageListUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithAccountUseCase
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EmailSignUpViewModelTest {
    @MockK
    private lateinit var signUpUseCase: SignUpWithAccountUseCase

    @MockK
    private lateinit var fetchImageListUseCase: FetchUserImageListUseCase
    @MockK private lateinit var validateUseCase: ValidateSignUpUseCase
    private lateinit var viewModel: EmailSignUpViewModel

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        MockKAnnotations.init(this)
        viewModel = EmailSignUpViewModel(
            signUpUseCase = signUpUseCase,
            fetchImageListUseCase = fetchImageListUseCase,
            validateUseCase = validateUseCase
        )

        coEvery {
            fetchImageListUseCase()
        } returns Result.success(
            FetchUserImageListUseCase.Response(
                listOf(
                    User.Image(id = 1, path = "path1"),
                    User.Image(id = 2, path = "path2"),
                    User.Image(id = 3, path = "path3"),
                    User.Image(id = 4, path = "path4")
                )
            )
        )
    }

    @Test
    fun nextPage_validInputs_signUpSuccess(): Unit = scope.runTest {
        coEvery {
            validateUseCase(any())
        } returns Result.success(Unit)

        coEvery {
            signUpUseCase(any())
        } returns Result.success(Unit)

        val eventDeferred = async { viewModel.eventFlow.first() }

        delay(100)
        viewModel.firstFieldFlow.value = "username"
        waitForValidationSuccess()
        viewModel.nextPage()
        delay(100)

        viewModel.firstFieldFlow.value = "email"
        waitForValidationSuccess()
        viewModel.nextPage()
        delay(100)

        viewModel.firstFieldFlow.value = "password"
        viewModel.secondFieldFlow.value = "password"
        waitForValidationSuccess()
        waitForValidationSuccess(false)
        viewModel.nextPage()
        delay(100)

        waitForValidationSuccess()
        viewModel.nextPage()
        delay(100)

        viewModel.firstFieldFlow.value = "nickname"
        waitForValidationSuccess()
        viewModel.nextPage()
        delay(100)

        assertEquals(SignUpEvent.Success, eventDeferred.await())
        coVerify { signUpUseCase(any()) }
    }

    @Test
    fun prevPage_nothing_remainField(): Unit = scope.runTest {
        val collectJob = launch {
            launch {
                viewModel.firstFieldFlow.collect()
            }
        }

        coEvery {
            validateUseCase(any())
        } returns Result.success(Unit)

        delay(100)
        viewModel.firstFieldFlow.value = "content"
        waitForValidationSuccess()

        viewModel.nextPage()
        delay(100)

        assertEquals("", viewModel.firstFieldFlow.value)
        viewModel.prevPage()
        delay(100)
        assertEquals("content", viewModel.firstFieldFlow.value)

        collectJob.cancelAndJoin()
    }

    private suspend fun waitForValidationSuccess(isFirst: Boolean = true) {
        scope.launch {
            val validationFlow = if (isFirst) {
                viewModel.firstValidationFlow
            } else {
                viewModel.secondValidationFlow
            }
            validationFlow.collect { status ->
                if (status == SignUpValidationState.Success) cancel()
            }
        }.join()
    }
}
