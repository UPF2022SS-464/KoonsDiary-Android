package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.Comment
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.AddCommentRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AddCommentUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<AddCommentRequest, EmptyResponse> {

    override suspend fun invoke(request: AddCommentRequest): Result<EmptyResponse> {
        if (request.content.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        val comment = Comment(
            diaryId = request.diaryId,
            content = request.content
        )

        return shareRepository.addComment(comment).map {
            EmptyResponse
        }
    }
}