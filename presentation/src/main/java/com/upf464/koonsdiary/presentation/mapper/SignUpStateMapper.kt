package com.upf464.koonsdiary.presentation.mapper

import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.presentation.model.account.UserEmailModel

internal fun SignUpError.toEmailSignUpState() = when (this) {
    is SignUpError.InvalidUsername -> UserEmailModel.State.INVALID_USERNAME
    is SignUpError.InvalidEmail -> UserEmailModel.State.INVALID_EMAIL
    is SignUpError.InvalidPassword -> UserEmailModel.State.INVALID_PASSWORD
    is SignUpError.InvalidNickname -> UserEmailModel.State.INVALID_NICKNAME
    is SignUpError.DuplicatedEmail -> UserEmailModel.State.DUPLICATED_EMAIL
    is SignUpError.DuplicatedUsername -> UserEmailModel.State.DUPLICATED_USERNAME
    else -> UserEmailModel.State.UNKNOWN
}
