package com.upf464.koonsdiary.presentation.ui.account.signup

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase

sealed class SignUpValidationState {
    object Success : SignUpValidationState()
    object Waiting : SignUpValidationState()
    object Invalid : SignUpValidationState()
    object Duplicated : SignUpValidationState()
    object Empty : SignUpValidationState()
    data class Unknown(val message: String?) : SignUpValidationState()
}

internal fun SignUpError.toValidationState() = when (this) {
    is SignUpError.InvalidUsername -> SignUpValidationState.Invalid
    is SignUpError.InvalidEmail -> SignUpValidationState.Invalid
    is SignUpError.InvalidPassword -> SignUpValidationState.Invalid
    is SignUpError.InvalidNickname -> SignUpValidationState.Invalid
    is SignUpError.DuplicatedEmail -> SignUpValidationState.Duplicated
    is SignUpError.DuplicatedUsername -> SignUpValidationState.Duplicated
    else -> SignUpValidationState.Unknown(message)
}

internal suspend fun validateToState(
    validateUseCase: ValidateSignUpUseCase,
    type: ValidateSignUpUseCase.Type,
    value: String
): SignUpValidationState {
    if (value.isEmpty()) return SignUpValidationState.Empty

    val error = validateUseCase(
        ValidateSignUpUseCase.Request(type, value)
    ).exceptionOrNull()

    return when (error) {
        null -> SignUpValidationState.Success
        is SignUpError -> error.toValidationState()
        else -> SignUpValidationState.Unknown(error.message)
    }
}
