package com.upf464.koonsdiary.presentation.ui.account.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.CommonError
import com.upf464.koonsdiary.domain.usecase.user.FetchUserImageListUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithAccountUseCase
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import com.upf464.koonsdiary.presentation.mapper.toPresentation
import com.upf464.koonsdiary.presentation.model.account.SignUpPage
import com.upf464.koonsdiary.presentation.model.account.UserImageModel
import com.upf464.koonsdiary.presentation.model.account.UserModel
import com.upf464.koonsdiary.presentation.model.account.UserValidationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EmailSignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpWithAccountUseCase,
    private val fetchImageListUseCase: FetchUserImageListUseCase,
    validateUseCase: ValidateSignUpUseCase
) : ViewModel() {

    private val userModel = UserModel.Email()
    private val validationModel = UserValidationModel.Email(
        validateUseCase,
        userModel.usernameFlow,
        userModel.emailFlow,
        userModel.passwordFlow,
        userModel.passwordConfirmFlow,
        userModel.imageFlow,
        userModel.nicknameFlow
    )

    private val pageList = listOf(
        SignUpPage.USERNAME,
        SignUpPage.EMAIL,
        SignUpPage.PASSWORD,
        SignUpPage.IMAGE,
        SignUpPage.NICKNAME
    )

    private val _pageIndexFlow = MutableStateFlow(0)
    val pageFlow = _pageIndexFlow.map { index ->
        pageList[index]
    }.stateIn(viewModelScope, SharingStarted.Lazily, pageList[0])

    val firstFieldFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val firstValidationFlow = pageFlow.flatMapLatest { page ->
        when (page) {
            SignUpPage.EMAIL -> validationModel.emailFlow
            SignUpPage.USERNAME -> validationModel.usernameFlow
            SignUpPage.PASSWORD -> validationModel.passwordFlow
            SignUpPage.IMAGE -> validationModel.imageFlow
            SignUpPage.NICKNAME -> validationModel.nicknameFlow
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, SignUpValidationState.Waiting)

    val secondFieldFlow = MutableStateFlow("")
    val secondValidationFlow = validationModel.passwordConfirmFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, SignUpValidationState.Waiting)

    private val _imageListFlow = MutableStateFlow(listOf<UserImageModel>())
    val imageListFlow = _imageListFlow.asStateFlow()

    private val _stateFlow = MutableStateFlow<SignUpState>(SignUpState.None)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            var job: Job? = null
            pageFlow.collect { page ->
                job?.cancel()
                val targetFlow = when (page) {
                    SignUpPage.EMAIL -> userModel.emailFlow
                    SignUpPage.USERNAME -> userModel.usernameFlow
                    SignUpPage.PASSWORD -> userModel.passwordFlow
                    SignUpPage.NICKNAME -> userModel.nicknameFlow
                    SignUpPage.IMAGE -> return@collect
                }
                job = connectFieldFlow(targetFlow, firstFieldFlow, this)
            }
        }

        connectFieldFlow(userModel.passwordConfirmFlow, secondFieldFlow, viewModelScope)

        viewModelScope.launch {
            fetchImageListUseCase().onSuccess { response ->
                _imageListFlow.value = response.imageList
                    .map { it.toPresentation(userModel.imageFlow, this) }
                    .also {
                        userModel.imageFlow.value = it.firstOrNull()
                    }
            }.onFailure { error ->
                handleError(error)
            }
        }
    }

    private fun connectFieldFlow(
        targetFlow: MutableStateFlow<String>,
        fieldFlow: MutableStateFlow<String>,
        scope: CoroutineScope
    ): Job {
        fieldFlow.value = targetFlow.value
        return scope.launch {
            fieldFlow.collect {
                targetFlow.value = it
            }
        }
    }

    fun selectImageAt(index: Int) {
        userModel.imageFlow.value = imageListFlow.value[index]
    }

    fun nextPage() {
        if (!isNextAvailable()) return

        val lastPageIdx = pageList.size - 1
        val currentPageIdx = _pageIndexFlow.value

        if (currentPageIdx == lastPageIdx) {
            signUp()
        } else {
            _pageIndexFlow.value = currentPageIdx + 1
        }
    }

    private fun isNextAvailable(): Boolean {
        val firstAvailable = firstValidationFlow.value == SignUpValidationState.Success
        val secondAvailable = pageFlow.value != SignUpPage.PASSWORD || secondValidationFlow.value == SignUpValidationState.Success
        return firstAvailable && secondAvailable
    }

    fun prevPage() {
        val currentPageIdx = _pageIndexFlow.value
        if (currentPageIdx == 0) return
        _pageIndexFlow.value = currentPageIdx - 1
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpWithAccountUseCase.Request(
                    email = userModel.emailFlow.value,
                    username = userModel.usernameFlow.value,
                    password = userModel.passwordFlow.value,
                    nickname = userModel.nicknameFlow.value,
                    imageId = userModel.imageFlow.value?.id ?: return@launch
                )
            ).onSuccess {
                _stateFlow.value = SignUpState.Success
            }.onFailure { error ->
                handleError(error)
            }
        }
    }

    private fun handleError(error: Throwable) {
        when (error) {
            CommonError.NetworkDisconnected -> _stateFlow.value = SignUpState.NoNetwork
            else -> _stateFlow.value = SignUpState.Failure
        }
    }
}
