package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.UpdateShareDiaryRequest
import com.upf464.koonsdiary.domain.response.share.UpdateShareDiaryResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class UpdateShareDiaryUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<UpdateShareDiaryRequest, UpdateShareDiaryResponse> {

    override suspend fun invoke(request: UpdateShareDiaryRequest): Result<UpdateShareDiaryResponse> {
        if (request.content.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        val diary = ShareDiary(
            id = request.diaryId,
            content = request.content,
            imageList = request.imageList
        )

        return shareRepository.updateDiary(diary).map { diaryId ->
            UpdateShareDiaryResponse(diaryId)
        }
    }
}