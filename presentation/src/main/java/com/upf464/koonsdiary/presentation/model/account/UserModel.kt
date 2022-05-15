package com.upf464.koonsdiary.presentation.model.account

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.ui.account.signup.SignUpValidationState
import com.upf464.koonsdiary.presentation.ui.account.signup.validateToState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

sealed class UserModel(
    validateUseCase: ValidateSignUpUseCase,
    val usernameFlow: StateFlow<String>,
    val imageFlow: StateFlow<User.Image?>,
    val nicknameFlow: StateFlow<String>
) {

    val usernameValidationFlow = usernameFlow.debounceMapWithDefault(
        Constants.SIGN_UP_DEBOUNCE_TIME,
        SignUpValidationState.Waiting
    ) {
        if (it.isEmpty()) SignUpValidationState.Empty
        else validateToState(validateUseCase, ValidateSignUpUseCase.Type.USERNAME, it)
    }

    val imageValidationFlow = imageFlow.map { image ->
        if (image != null) SignUpValidationState.Success
        else SignUpValidationState.Empty
    }

    val nicknameValidationFlow = nicknameFlow.map {
        if (it.isEmpty()) SignUpValidationState.Empty
        else validateToState(validateUseCase, ValidateSignUpUseCase.Type.NICKNAME, it)
    }

    class Kakao(
        validateUseCase: ValidateSignUpUseCase,
        usernameFlow: StateFlow<String>,
        imageFlow: StateFlow<User.Image?>,
        nicknameFlow: StateFlow<String>
    ) : UserModel(validateUseCase, usernameFlow, imageFlow, nicknameFlow)

    class Email(
        validateUseCase: ValidateSignUpUseCase,
        val emailFlow: StateFlow<String>,
        usernameFlow: StateFlow<String>,
        val passwordFlow: StateFlow<String>,
        val passwordConfirmFlow: StateFlow<String>,
        imageFlow: StateFlow<User.Image?>,
        nicknameFlow: StateFlow<String>
    ) : UserModel(validateUseCase, usernameFlow, imageFlow, nicknameFlow) {

        val emailValidationFlow = emailFlow.debounceMapWithDefault(
            Constants.SIGN_UP_DEBOUNCE_TIME,
            SignUpValidationState.Waiting
        ) {
            if (it.isEmpty()) SignUpValidationState.Empty
            else validateToState(validateUseCase, ValidateSignUpUseCase.Type.EMAIL, it)
        }

        val passwordValidationFlow = passwordFlow.map {
            if (it.isEmpty()) SignUpValidationState.Empty
            else validateToState(validateUseCase, ValidateSignUpUseCase.Type.PASSWORD, it)
        }

        val passwordConfirmValidationFlow = combine(passwordFlow, passwordConfirmFlow) { password, confirm ->
            if (password == confirm) SignUpValidationState.Success
            else SignUpValidationState.Invalid
        }
    }
}

@OptIn(FlowPreview::class)
private fun <T, R> Flow<T>.debounceMapWithDefault(
    timeoutMillis: Long,
    default: R,
    transform: suspend (T) -> R
): Flow<R> = channelFlow {
    onEach {
        send(default)
    }
        .debounce(timeoutMillis)
        .map(transform)
        .collect {
            send(it)
        }
}
