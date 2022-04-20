package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class AddShareDiaryUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        if (request.content.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        val diary = ShareDiary(
            group = ShareGroup(id = request.groupId),
            content = request.content,
            imageList = request.imageList
        )

        return shareRepository.addDiary(diary).map { diaryId ->
            Response(diaryId)
        }
    }

    data class Request(
        val groupId: Int,
        val content: String,
        val imageList: List<DiaryImage>
    )

    data class Response(
        val diaryId: Int
    )
}
