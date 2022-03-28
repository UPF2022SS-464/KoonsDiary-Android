package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.common.DiaryValidator
import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.AddDiaryRequest
import com.upf464.koonsdiary.domain.response.AddDiaryResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AddDiaryUseCase @Inject constructor(
    private val validator: DiaryValidator,
    private val diaryRepository: DiaryRepository
) : ResultUseCase<AddDiaryRequest, AddDiaryResponse> {

    override suspend fun invoke(request: AddDiaryRequest): Result<AddDiaryResponse> {
        if (!validator.validateContent(request.content)) {
            return Result.failure(DiaryError.EmptyContent)
        }

        val diary = Diary(
            date = request.date,
            content = request.content,
            sentiment = request.sentiment,
            imageList = request.imageList
        )

        return diaryRepository.addDiary(diary).map { diaryId ->
            AddDiaryResponse(diaryId)
        }
    }
}