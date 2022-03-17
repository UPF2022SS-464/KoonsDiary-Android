package com.upf464.koonsdiary.domain.common

inline fun <T, R> Result<T>.flatMap(
    transform: (T) -> Result<R>
): Result<R> {
    return when (val exception = exceptionOrNull()) {
        null -> transform(getOrThrow())
        else -> Result.failure(exception)
    }
}

inline fun <T> Result<T>.errorMap(
    transform: (Throwable) -> Exception
): Result<T> {
    return when (val exception = exceptionOrNull()) {
        null -> this
        else -> Result.failure(transform(exception))
    }
}