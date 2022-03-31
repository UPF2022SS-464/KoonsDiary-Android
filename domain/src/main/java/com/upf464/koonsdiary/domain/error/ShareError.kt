package com.upf464.koonsdiary.domain.error

sealed class ShareError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    object EmptyContent : ShareError()

    object InvalidGroupName : ShareError()

    object InvalidGroupId : ShareError()

    object InvalidDiaryId : ShareError()
}