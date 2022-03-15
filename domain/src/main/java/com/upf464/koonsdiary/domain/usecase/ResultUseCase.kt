package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.request.Request
import com.upf464.koonsdiary.domain.response.Response

interface ResultUseCase<R: Request, T: Response> {

    suspend operator fun invoke(request: R): Result<T>
}