package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.common.DiaryValidator
import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import java.time.LocalDate
import javax.inject.Inject

class AddDiaryUseCase @Inject constructor(
    private val validator: DiaryValidator,
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
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
            Response(diaryId)
        }
    }

    data class Request(
        val date: LocalDate,
        val content: String,
        val sentiment: Sentiment,
        val imageList: List<DiaryImage>
    )

    data class Response(
        val diaryId: Int
    )
}
