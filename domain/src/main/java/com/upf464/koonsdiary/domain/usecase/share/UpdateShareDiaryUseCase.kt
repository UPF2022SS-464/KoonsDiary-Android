package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class UpdateShareDiaryUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        if (request.content.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        val diary = ShareDiary(
            id = request.diaryId,
            content = request.content,
            imageList = request.imageList
        )

        return shareRepository.updateDiary(diary).map { diaryId ->
            Response(diaryId)
        }
    }

    data class Request(
        val diaryId: Int,
        val content: String,
        val imageList: List<DiaryImage>
    )

    data class Response(
        val diaryId: Int
    )
}
