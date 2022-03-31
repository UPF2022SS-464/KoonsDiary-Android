package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.DeleteCommentRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class DeleteCommentUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<DeleteCommentRequest, EmptyResponse> {

    override suspend fun invoke(request: DeleteCommentRequest): Result<EmptyResponse> {
        return shareRepository.deleteComment(request.commentId).map {
            EmptyResponse
        }
    }
}