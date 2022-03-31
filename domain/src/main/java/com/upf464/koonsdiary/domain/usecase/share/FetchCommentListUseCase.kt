package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.FetchCommentListRequest
import com.upf464.koonsdiary.domain.response.share.FetchCommentListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchCommentListUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<FetchCommentListRequest, FetchCommentListResponse> {

    override suspend fun invoke(request: FetchCommentListRequest): Result<FetchCommentListResponse> {
        return shareRepository.fetchCommentList(request.diaryId).map { commentList ->
            FetchCommentListResponse(commentList)
        }
    }
}