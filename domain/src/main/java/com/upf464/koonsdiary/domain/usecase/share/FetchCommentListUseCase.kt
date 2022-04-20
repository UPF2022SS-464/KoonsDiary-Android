package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.model.Comment
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class FetchCommentListUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        return shareRepository.fetchCommentList(request.diaryId).map { commentList ->
            Response(commentList)
        }
    }

    data class Request(
        val diaryId: Int
    )

    data class Response(
        val commentList: List<Comment>
    )
}
