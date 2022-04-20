package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.model.DiaryPreview
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import javax.inject.Inject

class FetchDiaryPreviewUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        return diaryRepository.fetchDiaryPreview(request.diaryId).map { preview ->
            Response(preview)
        }
    }

    data class Request(
        val diaryId: Int
    )

    data class Response(
        val preview: DiaryPreview
    )
}
