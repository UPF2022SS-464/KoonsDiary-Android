package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.request.Request
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<R: Request, T> {

    operator fun invoke(request: R): Flow<T>
}