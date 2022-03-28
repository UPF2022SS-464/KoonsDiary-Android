package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.diary.FetchDiaryRequest
import com.upf464.koonsdiary.domain.response.diary.FetchDiaryResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ResultUseCase<FetchDiaryRequest, FetchDiaryResponse> {

    override suspend fun invoke(request: FetchDiaryRequest): Result<FetchDiaryResponse> {
        return diaryRepository.fetchDiary(request.diaryId).map { diary ->
            FetchDiaryResponse(diary)
        }
    }
}