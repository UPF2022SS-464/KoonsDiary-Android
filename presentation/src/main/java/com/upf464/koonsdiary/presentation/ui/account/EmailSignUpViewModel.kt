package com.upf464.koonsdiary.presentation.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.request.user.SignUpWithUsernameRequest
import com.upf464.koonsdiary.domain.request.user.ValidateSignUpRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.presentation.model.account.UserEmailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@FlowPreview
internal class EmailSignUpViewModel @Inject constructor(
    private val signUpUseCase: ResultUseCase<SignUpWithUsernameRequest, EmptyResponse>,
    validateUseCase: ResultUseCase<ValidateSignUpRequest, EmptyResponse>
) : ViewModel() {

    private val userModel = UserEmailModel(validateUseCase)
    private val _pageFlow = MutableStateFlow(SignUpPage.EMAIL)
    val pageFlow = _pageFlow.asStateFlow()

    enum class SignUpPage {
        EMAIL,
        USERNAME,
        PASSWORD,
        PROFILE,
        NICKNAME
    }

    private val _eventFlow = MutableSharedFlow<SignUpEvent>(extraBufferCapacity = 1)
    val eventFlow: Flow<SignUpEvent> = _eventFlow

    sealed class SignUpEvent {

        object NoImageSelected : SignUpEvent()

        object Success : SignUpEvent()
    }

    fun nextPage() {
        val lastPageIdx = SignUpPage.values().size - 1
        val currentPageIdx = _pageFlow.value.ordinal
        if (currentPageIdx == lastPageIdx) return

        _pageFlow.value = SignUpPage.values()[currentPageIdx + 1]
    }

    fun prevPage() {
        val currentPageIdx = _pageFlow.value.ordinal
        if (currentPageIdx == 0) return
        _pageFlow.value = SignUpPage.values()[currentPageIdx - 1]
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpWithUsernameRequest(
                    email = userModel.emailFlow.value,
                    username = userModel.usernameFlow.value,
                    password = userModel.passwordFlow.value,
                    nickname = userModel.nicknameFlow.value,
                    imageId = userModel.imageFlow.value?.id ?: run {
                        setEvent(SignUpEvent.NoImageSelected)
                        return@launch
                    }
                )
            ).onSuccess {
                setEvent(SignUpEvent.Success)
            }
        }
    }

    private fun setEvent(event: SignUpEvent) {
        _eventFlow.tryEmit(event)
    }
}
