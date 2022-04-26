package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.model.DiaryPreview
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import java.time.LocalDate
import javax.inject.Inject

class FetchDiaryPreviewUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        return diaryRepository.fetchDiaryPreview(request.date).map { preview ->
            Response(preview)
        }
    }

    data class Request(
        val date: LocalDate
    )

    data class Response(
        val preview: DiaryPreview
    )
}
