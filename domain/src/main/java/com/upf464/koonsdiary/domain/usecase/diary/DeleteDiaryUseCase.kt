package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.repository.DiaryRepository
import javax.inject.Inject

class DeleteDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return diaryRepository.deleteDiary(request.diaryId)
    }

    data class Request(
        val diaryId: Int
    )
}
