package com.upf464.koonsdiary.presentation.mapper

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.presentation.model.account.SignUpValidationState

internal fun SignUpError.toEmailSignUpState() = when (this) {
    is SignUpError.InvalidUsername -> SignUpValidationState.INVALID_USERNAME
    is SignUpError.InvalidEmail -> SignUpValidationState.INVALID_EMAIL
    is SignUpError.InvalidPassword -> SignUpValidationState.INVALID_PASSWORD
    is SignUpError.InvalidNickname -> SignUpValidationState.INVALID_NICKNAME
    is SignUpError.DuplicatedEmail -> SignUpValidationState.DUPLICATED_EMAIL
    is SignUpError.DuplicatedUsername -> SignUpValidationState.DUPLICATED_USERNAME
    else -> SignUpValidationState.UNKNOWN
}

internal fun SignUpError.toKakaoSignUpState() = when (this) {
    is SignUpError.InvalidUsername -> SignUpValidationState.INVALID_USERNAME
    is SignUpError.InvalidEmail -> SignUpValidationState.INVALID_EMAIL
    is SignUpError.InvalidPassword -> SignUpValidationState.INVALID_PASSWORD
    is SignUpError.InvalidNickname -> SignUpValidationState.INVALID_NICKNAME
    is SignUpError.DuplicatedEmail -> SignUpValidationState.DUPLICATED_EMAIL
    is SignUpError.DuplicatedUsername -> SignUpValidationState.DUPLICATED_USERNAME
    else -> SignUpValidationState.UNKNOWN
}
