package com.upf464.koonsdiary.presentation.model.account

import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.ui.account.signup.SignUpValidationState
import com.upf464.koonsdiary.presentation.ui.account.signup.validateToState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

sealed class UserValidationModel(
    validateUseCase: ValidateSignUpUseCase,
    _usernameFlow: Flow<String>,
    _imageFlow: Flow<UserImageModel?>,
    _nicknameFlow: Flow<String>
) {

    val usernameFlow = _usernameFlow.debounceMapWithDefault(
        Constants.SIGN_UP_DEBOUNCE_TIME,
        SignUpValidationState.Waiting
    ) {
        if (it.isEmpty()) SignUpValidationState.Empty
        else validateToState(validateUseCase, ValidateSignUpUseCase.Type.USERNAME, it)
    }

    val imageFlow = _imageFlow.map { image ->
        if (image != null) SignUpValidationState.Success
        else SignUpValidationState.Empty
    }

    val nicknameFlow = _nicknameFlow.map {
        if (it.isEmpty()) SignUpValidationState.Empty
        else validateToState(validateUseCase, ValidateSignUpUseCase.Type.NICKNAME, it)
    }

    class Kakao(
        validateUseCase: ValidateSignUpUseCase,
        _usernameFlow: Flow<String>,
        _imageFlow: Flow<UserImageModel?>,
        _nicknameFlow: Flow<String>
    ) : UserValidationModel(
        validateUseCase,
        _usernameFlow,
        _imageFlow,
        _nicknameFlow
    )

    class Email(
        validateUseCase: ValidateSignUpUseCase,
        _usernameFlow: Flow<String>,
        _emailFlow: Flow<String>,
        _passwordFlow: Flow<String>,
        _passwordConfirmFlow: Flow<String>,
        _imageFlow: Flow<UserImageModel?>,
        _nicknameFlow: Flow<String>
    ) : UserValidationModel(
        validateUseCase,
        _usernameFlow,
        _imageFlow,
        _nicknameFlow
    ) {

        val emailFlow = _emailFlow.debounceMapWithDefault(
            Constants.SIGN_UP_DEBOUNCE_TIME,
            SignUpValidationState.Waiting
        ) {
            if (it.isEmpty()) SignUpValidationState.Empty
            else validateToState(validateUseCase, ValidateSignUpUseCase.Type.EMAIL, it)
        }

        val passwordFlow = _passwordFlow.map {
            if (it.isEmpty()) SignUpValidationState.Empty
            else validateToState(validateUseCase, ValidateSignUpUseCase.Type.PASSWORD, it)
        }

        val passwordConfirmFlow = combine(_passwordFlow, _passwordConfirmFlow) { password, confirm ->
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
