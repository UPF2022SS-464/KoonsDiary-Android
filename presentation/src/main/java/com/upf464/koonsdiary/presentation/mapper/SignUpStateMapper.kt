package com.upf464.koonsdiary.presentation.mapper

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.presentation.model.account.SignUpState

internal fun SignUpError.toEmailSignUpState() = when (this) {
    is SignUpError.InvalidUsername -> SignUpState.INVALID_USERNAME
    is SignUpError.InvalidEmail -> SignUpState.INVALID_EMAIL
    is SignUpError.InvalidPassword -> SignUpState.INVALID_PASSWORD
    is SignUpError.InvalidNickname -> SignUpState.INVALID_NICKNAME
    is SignUpError.DuplicatedEmail -> SignUpState.DUPLICATED_EMAIL
    is SignUpError.DuplicatedUsername -> SignUpState.DUPLICATED_USERNAME
    else -> SignUpState.UNKNOWN
}

internal fun SignUpError.toKakaoSignUpState() = when (this) {
    is SignUpError.InvalidUsername -> SignUpState.INVALID_USERNAME
    is SignUpError.InvalidEmail -> SignUpState.INVALID_EMAIL
    is SignUpError.InvalidPassword -> SignUpState.INVALID_PASSWORD
    is SignUpError.InvalidNickname -> SignUpState.INVALID_NICKNAME
    is SignUpError.DuplicatedEmail -> SignUpState.DUPLICATED_EMAIL
    is SignUpError.DuplicatedUsername -> SignUpState.DUPLICATED_USERNAME
    else -> SignUpState.UNKNOWN
}
