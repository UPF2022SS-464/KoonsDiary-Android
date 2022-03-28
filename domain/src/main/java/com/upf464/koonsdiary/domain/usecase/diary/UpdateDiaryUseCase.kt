package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.common.DiaryValidator
import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.diary.UpdateDiaryRequest
import com.upf464.koonsdiary.domain.response.diary.UpdateDiaryResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class UpdateDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val validator: DiaryValidator
) : ResultUseCase<UpdateDiaryRequest, UpdateDiaryResponse> {

    override suspend fun invoke(request: UpdateDiaryRequest): Result<UpdateDiaryResponse> {
        if (!validator.validateContent(request.content)) {
            return Result.failure(DiaryError.EmptyContent)
        }

        val diary = Diary(
            id = request.diaryId,
            date = request.date,
            content = request.content,
            sentiment = request.sentiment,
            imageList = request.imageList
        )

        return diaryRepository.updateDiary(diary).map { diaryId ->
            UpdateDiaryResponse(diaryId)
        }
    }
}