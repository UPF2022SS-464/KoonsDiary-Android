package com.upf464.koonsdiary.presentation.model.account

enum class SignUpState {
    WAITING,
    SUCCESS,
    INVALID_USERNAME,
    INVALID_PASSWORD,
    DIFFERENT_CONFIRM,
    UNSELECTED_IMAGE,
    INVALID_EMAIL,
    INVALID_NICKNAME,
    DUPLICATED_EMAIL,
    DUPLICATED_USERNAME,
    UNKNOWN
}
