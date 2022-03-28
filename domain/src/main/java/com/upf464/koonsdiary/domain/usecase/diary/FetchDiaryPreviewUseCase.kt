package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.diary.FetchDiaryPreviewRequest
import com.upf464.koonsdiary.domain.response.diary.FetchDiaryPreviewResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchDiaryPreviewUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
): ResultUseCase<FetchDiaryPreviewRequest, FetchDiaryPreviewResponse> {

    override suspend fun invoke(request: FetchDiaryPreviewRequest): Result<FetchDiaryPreviewResponse> {
        return diaryRepository.fetchDiaryPreview(request.diaryId).map { preview ->
            FetchDiaryPreviewResponse(preview)
        }
    }
}