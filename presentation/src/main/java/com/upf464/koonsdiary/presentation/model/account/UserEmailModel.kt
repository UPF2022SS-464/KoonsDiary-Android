package com.upf464.koonsdiary.presentation.model.account

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.request.user.ValidateSignUpRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
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
    val useCase: ResultUseCase<ValidateSignUpRequest, EmptyResponse>,
    val usernameFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val emailFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val passwordFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val passwordConfirmFlow: MutableStateFlow<String> = MutableStateFlow(""),
    val imageFlow: MutableStateFlow<UserImageModel?> = MutableStateFlow(null),
    val nicknameFlow: MutableStateFlow<String> = MutableStateFlow("")
) {

    val emailValidFlow: Flow<SignUpState> = waitFirstFlow(emailFlow) {
        val error = useCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.EMAIL, it))
            .exceptionOrNull() ?: return@waitFirstFlow SignUpState.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: SignUpState.UNKNOWN
    }

    val usernameValidFlow: Flow<SignUpState> = waitFirstFlow(usernameFlow) {
        val error = useCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.USERNAME, it))
            .exceptionOrNull() ?: return@waitFirstFlow SignUpState.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: SignUpState.UNKNOWN
    }

    @OptIn(FlowPreview::class)
    private fun waitFirstFlow(source: Flow<String>, mapBlock: suspend (String) -> SignUpState) =
        channelFlow {
            source.onEach {
                send(SignUpState.WAITING)
            }.debounce(Constants.SIGN_UP_DEBOUNCE_TIME)
                .map(mapBlock)
                .collect {
                    send(it)
                }
        }

    val passwordValidFlow: Flow<SignUpState> = passwordFlow.map {
        val error = useCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.PASSWORD, it))
            .exceptionOrNull() ?: return@map SignUpState.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: SignUpState.UNKNOWN
    }

    val passwordConfirmValidFlow: Flow<SignUpState> =
        combine(passwordFlow, passwordConfirmFlow) { password, confirm ->
            if (password == confirm) SignUpState.SUCCESS
            else SignUpState.DIFFERENT_CONFIRM
        }

    val imageValidFlow: Flow<SignUpState> = imageFlow.map { image ->
        if (image != null) SignUpState.SUCCESS else SignUpState.UNSELECTED_IMAGE
    }

    val nicknameValidFlow: Flow<SignUpState> = nicknameFlow.map {
        val error = useCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.NICKNAME, it))
            .exceptionOrNull() ?: return@map SignUpState.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: SignUpState.UNKNOWN
    }
}
