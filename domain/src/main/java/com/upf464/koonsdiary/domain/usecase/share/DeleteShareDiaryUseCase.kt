package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.DeleteShareDiaryRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class DeleteShareDiaryUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<DeleteShareDiaryRequest, EmptyResponse> {

    override suspend fun invoke(request: DeleteShareDiaryRequest): Result<EmptyResponse> {
        return shareRepository.deleteDiary(request.diaryId).map {
            EmptyResponse
        }
    }
}