package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.DeleteDiaryRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class DeleteDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ResultUseCase<DeleteDiaryRequest, EmptyResponse> {

    override suspend fun invoke(request: DeleteDiaryRequest): Result<EmptyResponse> {
        return diaryRepository.deleteDiary(request.diaryId).map {
            EmptyResponse
        }
    }
}