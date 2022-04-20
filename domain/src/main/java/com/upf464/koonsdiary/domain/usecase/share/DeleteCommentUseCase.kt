package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return shareRepository.deleteComment(request.commentId)
    }

    data class Request(
        val commentId: Int
    )
}
