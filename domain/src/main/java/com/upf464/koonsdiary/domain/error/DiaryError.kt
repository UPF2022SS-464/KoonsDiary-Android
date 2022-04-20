package com.upf464.koonsdiary.domain.error

sealed class DiaryError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    object EmptyContent : DiaryError()

    object InvalidDiaryId : DiaryError()
}
