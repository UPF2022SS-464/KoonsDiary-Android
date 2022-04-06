package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.request.Request
import com.upf464.koonsdiary.domain.response.Response
import kotlinx.coroutines.flow.Flow

interface FlowResultUseCase<R: Request, T: Response> {

    operator fun invoke(request: R): Flow<Result<T>>
}