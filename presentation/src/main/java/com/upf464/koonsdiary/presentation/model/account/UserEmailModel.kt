package com.upf464.koonsdiary.presentation.model.account

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.mapper.toEmailSignUpState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal data class UserEmailModel(
    val validateUseCase: ValidateSignUpUseCase,
    val usernameFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val emailFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val passwordFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val passwordConfirmFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val imageFlow: MutableStateFlow<UserImageModel?> = MutableStateFlow(null),
    val nicknameFlow: MutableStateFlow<String> = MutableStateFlow("")
) {

    val emailValidFlow: Flow<SignUpValidationState> = waitFirstFlow(emailFlow) {
        val error = validateUseCase(
            ValidateSignUpUseCase.Request(ValidateSignUpUseCase.Request.Type.EMAIL, it)
        ).exceptionOrNull() ?: return@waitFirstFlow SignUpValidationState.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: SignUpValidationState.UNKNOWN
    }

    val usernameValidFlow: Flow<SignUpValidationState> = waitFirstFlow(usernameFlow) {
        val error = validateUseCase(
            ValidateSignUpUseCase.Request(ValidateSignUpUseCase.Request.Type.USERNAME, it)
        ).exceptionOrNull() ?: return@waitFirstFlow SignUpValidationState.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: SignUpValidationState.UNKNOWN
    }

    @OptIn(FlowPreview::class)
    private fun waitFirstFlow(source: Flow<String>, mapBlock: suspend (String) -> SignUpValidationState) =
        channelFlow {
            source.onEach {
                send(SignUpValidationState.WAITING)
            }.debounce(Constants.SIGN_UP_DEBOUNCE_TIME)
                .map(mapBlock)
                .collect {
                    send(it)
                }
        }

    val passwordValidFlow: Flow<SignUpValidationState> = passwordFlow.map {
        val error = validateUseCase(
            ValidateSignUpUseCase.Request(ValidateSignUpUseCase.Request.Type.PASSWORD, it)
        ).exceptionOrNull() ?: return@map SignUpValidationState.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: SignUpValidationState.UNKNOWN
    }

    val passwordConfirmValidFlow: Flow<SignUpValidationState> =
        combine(passwordFlow, passwordConfirmFlow) { password, confirm ->
            if (password == confirm) SignUpValidationState.SUCCESS
            else SignUpValidationState.DIFFERENT_CONFIRM
        }

    val imageValidFlow: Flow<SignUpValidationState> = imageFlow.map { image ->
        if (image != null) SignUpValidationState.SUCCESS else SignUpValidationState.UNSELECTED_IMAGE
    }

    val nicknameValidFlow: Flow<SignUpValidationState> = nicknameFlow.map {
        val error = validateUseCase(
            ValidateSignUpUseCase.Request(ValidateSignUpUseCase.Request.Type.NICKNAME, it)
        ).exceptionOrNull() ?: return@map SignUpValidationState.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: SignUpValidationState.UNKNOWN
    }
}
