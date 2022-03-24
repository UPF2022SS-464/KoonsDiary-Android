package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.AddDiaryRequest
import com.upf464.koonsdiary.domain.response.AddDiaryResponse
import javax.inject.Inject

internal class AddDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ResultUseCase<AddDiaryRequest, AddDiaryResponse> {

    override suspend fun invoke(request: AddDiaryRequest): Result<AddDiaryResponse> {
        val diary = Diary(
            date = request.date,
            content = request.content,
            sentiment = request.sentiment,
            imageList = request.imageList
        )

        return diaryRepository.addDiary(diary).flatMap { diaryId ->
            Result.success(AddDiaryResponse(diaryId))
        }
    }
}