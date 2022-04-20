package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class DeleteShareDiaryUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return shareRepository.deleteDiary(request.diaryId)
    }

    data class Request(
        val diaryId: Int
    )
}
