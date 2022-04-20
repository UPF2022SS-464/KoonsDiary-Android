package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class FetchShareDiaryListUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        return shareRepository.fetchDiaryList(request.groupId).map { diaryList ->
            Response(diaryList)
        }
    }

    data class Request(
        val groupId: Int
    )

    data class Response(
        val diaryList: List<ShareDiary>
    )
}
