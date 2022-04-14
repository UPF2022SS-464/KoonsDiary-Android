package com.upf464.koonsdiary.presentation.ui.account.signup

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.request.user.FetchUserImageListRequest
import com.upf464.koonsdiary.domain.request.user.SignUpWithKakaoRequest
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KakaoSignUpViewModelTest {

    @MockK private lateinit var signUpUseCase: ResultUseCase<SignUpWithKakaoRequest, EmptyResponse>
    @MockK private lateinit var fetchImageListUseCase:
            ResultUseCase<FetchUserImageListRequest, FetchUserImageListResponse>
    @MockK private lateinit var validateUseCase: ResultUseCase<ValidateSignUpRequest, EmptyResponse>
    private lateinit var viewModel: KakaoSignUpViewModel

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        MockKAnnotations.init(this)
        viewModel = KakaoSignUpViewModel(
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

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun nextPage_validInputs_signUpSuccess(): Unit = scope.runTest {
        coEvery {
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.USERNAME, ""))
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
        viewModel.nextPage()
        viewModel.nextPage()

        assertEquals(KakaoSignUpViewModel.SignUpEvent.Success, eventDeferred.await())
        coVerify { signUpUseCase(any()) }
    }

    @Test
    fun nextPage_invalidUsername_remainAtUsername(): Unit = scope.runTest {
        coEvery {
            validateUseCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.USERNAME, ""))
        } returns Result.failure(SignUpError.InvalidUsername)

        waitForValidationChange()
        viewModel.nextPage()

        assertEquals(KakaoSignUpViewModel.SignUpPage.USERNAME, viewModel.pageFlow.value)
    }

    private suspend fun waitForValidationSuccess() {
        scope.launch {
            viewModel.validationFlow.collect { status ->
                if (status == SignUpState.SUCCESS) cancel()
            }
        }.join()
    }

    private suspend fun waitForValidationChange() {
        scope.launch {
            val first = viewModel.validationFlow.value
            viewModel.validationFlow.collect { status ->
                if (status != first) cancel()
            }
        }.join()
    }
}
