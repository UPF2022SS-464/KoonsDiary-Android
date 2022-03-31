package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.AddShareDiaryRequest
import com.upf464.koonsdiary.domain.response.share.AddShareDiaryResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AddShareDiaryUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<AddShareDiaryRequest, AddShareDiaryResponse> {

    override suspend fun invoke(request: AddShareDiaryRequest): Result<AddShareDiaryResponse> {
        if (request.content.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        val diary = ShareDiary(
            group = ShareGroup(id = request.groupId),
            content = request.content,
            imageList = request.imageList
        )

        return shareRepository.addDiary(diary).map { diaryId ->
            AddShareDiaryResponse(diaryId)
        }
    }
}