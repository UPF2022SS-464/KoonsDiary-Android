package com.upf464.koonsdiary.presentation.model.account

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.request.user.ValidateSignUpRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
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
    val imageFlow: MutableStateFlow<User.Image?> = MutableStateFlow(null),
    val nicknameFlow: MutableStateFlow<String> = MutableStateFlow("")
) {

    val emailValidFlow: Flow<State> = waitFirstFlow(emailFlow) {
        val error = useCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.EMAIL, it))
            .exceptionOrNull() ?: return@waitFirstFlow State.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: State.UNKNOWN
    }

    val usernameValidFlow: Flow<State> = waitFirstFlow(usernameFlow) {
        val error = useCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.USERNAME, it))
            .exceptionOrNull() ?: return@waitFirstFlow State.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: State.UNKNOWN
    }

    @OptIn(FlowPreview::class)
    private fun waitFirstFlow(source: Flow<String>, mapBlock: suspend (String) -> State) =
        channelFlow {
            source.onEach {
                send(State.WAITING)
            }.debounce(DEBOUNCE_TIME)
                .map(mapBlock)
                .collect {
                    send(it)
                }
        }

    val passwordValidFlow: Flow<State> = passwordFlow.map {
        val error = useCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.PASSWORD, it))
            .exceptionOrNull() ?: return@map State.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: State.UNKNOWN
    }

    val passwordConfirmValidFlow: Flow<State> =
        combine(passwordFlow, passwordConfirmFlow) { password, confirm ->
            if (password == confirm) State.SUCCESS
            else State.DIFFERENT_CONFIRM
        }

    val nicknameValidFlow: Flow<State> = nicknameFlow.map {
        val error = useCase(ValidateSignUpRequest(ValidateSignUpRequest.Type.NICKNAME, it))
            .exceptionOrNull() ?: return@map State.SUCCESS
        (error as? SignUpError)?.toEmailSignUpState() ?: State.UNKNOWN
    }

    enum class State {
        WAITING,
        SUCCESS,
        INVALID_USERNAME,
        INVALID_PASSWORD,
        DIFFERENT_CONFIRM,
        INVALID_EMAIL,
        INVALID_NICKNAME,
        DUPLICATED_EMAIL,
        DUPLICATED_USERNAME,
        UNKNOWN
    }

    companion object {

        private const val DEBOUNCE_TIME = 1000L
    }
}
