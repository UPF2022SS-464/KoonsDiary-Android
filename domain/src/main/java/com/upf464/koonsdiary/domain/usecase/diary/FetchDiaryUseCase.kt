package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import javax.inject.Inject

class FetchDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        return diaryRepository.fetchDiary(request.diaryId).map { diary ->
            Response(diary)
        }
    }

    data class Request(
        val diaryId: Int
    )

    data class Response(
        val diary: Diary
    )
}
