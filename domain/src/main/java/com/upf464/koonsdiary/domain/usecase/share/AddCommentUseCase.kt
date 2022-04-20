package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.Comment
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        if (request.content.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        val comment = Comment(
            diaryId = request.diaryId,
            content = request.content
        )

        return shareRepository.addComment(comment)
    }

    data class Request(
        val diaryId: Int,
        val content: String
    )
}
