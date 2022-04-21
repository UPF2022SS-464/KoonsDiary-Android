package com.upf464.koonsdiary.presentation.ui.account.signup

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.usecase.user.FetchUserImageListUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithKakaoUseCase
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import com.upf464.koonsdiary.presentation.model.account.SignUpPage
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
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

    @MockK private lateinit var signUpUseCase: SignUpWithKakaoUseCase
    @MockK private lateinit var fetchImageListUseCase: FetchUserImageListUseCase
    @MockK private lateinit var validateUseCase: ValidateSignUpUseCase
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

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
        viewModel.fieldFlow.value = "username"
        waitForValidationSuccess()
        viewModel.nextPage()
        delay(100)

        viewModel.nextPage()
        delay(100)

        viewModel.fieldFlow.value = "nickname"
        delay(100)
        viewModel.nextPage()

        assertEquals(SignUpEvent.Success, eventDeferred.await())
        coVerify { signUpUseCase(any()) }
    }

    @Test
    fun nextPage_invalidUsername_remainAtUsername(): Unit = scope.runTest {
        coEvery {
            validateUseCase(any())
        } returns Result.failure(SignUpError.InvalidUsername)

        waitForValidationChange()
        viewModel.nextPage()
        delay(100)

        assertEquals(SignUpPage.USERNAME, viewModel.pageFlow.value)
    }

    private suspend fun waitForValidationSuccess() {
        scope.launch {
            viewModel.validationFlow.collect { status ->
                if (status == SignUpValidationState.Success) cancel()
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
