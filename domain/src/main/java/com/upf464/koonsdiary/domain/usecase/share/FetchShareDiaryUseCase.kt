package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class FetchShareDiaryUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        return shareRepository.fetchDiary(request.diaryId).map { diary ->
            Response(diary)
        }
    }

    data class Request(
        val diaryId: Int
    )

    data class Response(
        val diary: ShareDiary
    )
}
