package com.upf464.koonsdiary.presentation.ui.account.signup

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.request.user.FetchUserImageListRequest
import com.upf464.koonsdiary.domain.request.user.SignUpWithUsernameRequest
import com.upf464.koonsdiary.domain.request.user.ValidateSignUpRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.response.user.FetchUserImageListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.presentation.model.account.SignUpState
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
    private lateinit var signUpUseCase: ResultUseCase<SignUpWithUsernameRequest, EmptyResponse>

    @MockK
    private lateinit var fetchImageListUseCase: ResultUseCase<FetchUserImageListRequest, FetchUserImageListResponse>
    @MockK private lateinit var validateUseCase: ResultUseCase<ValidateSignUpRequest, EmptyResponse>
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
            fetchImageListUseCase(FetchUserImageListRequest)
        } returns Result.success(
            FetchUserImageListResponse(
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
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.EMAIL, ""))
        } returns Result.success(EmptyResponse)

        coEvery {
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.USERNAME, ""))
        } returns Result.success(EmptyResponse)

        coEvery {
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.PASSWORD, ""))
        } returns Result.success(EmptyResponse)

        coEvery {
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.NICKNAME, ""))
        } returns Result.success(EmptyResponse)

        coEvery {
            signUpUseCase(any())
        } returns Result.success(EmptyResponse)

        val eventDeferred = async { viewModel.eventFlow.first() }

        waitForValidationSuccess()
        viewModel.nextPage()
        waitForValidationSuccess()
        viewModel.nextPage()
        waitForValidationSuccess()
        waitForValidationSuccess(false)
        viewModel.nextPage()
        waitForValidationSuccess()
        viewModel.nextPage()
        waitForValidationSuccess()
        viewModel.nextPage()

        assertEquals(EmailSignUpViewModel.SignUpEvent.Success, eventDeferred.await())
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
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.EMAIL, ""))
        } returns Result.success(EmptyResponse)

        coEvery {
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.EMAIL, "content"))
        } returns Result.success(EmptyResponse)

        coEvery {
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.USERNAME, ""))
        } returns Result.success(EmptyResponse)

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
                if (status == SignUpState.SUCCESS) cancel()
            }
        }.join()
    }
}
