package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.FetchShareDiaryListRequest
import com.upf464.koonsdiary.domain.response.share.FetchShareDiaryListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchShareDiaryListUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<FetchShareDiaryListRequest, FetchShareDiaryListResponse> {

    override suspend fun invoke(request: FetchShareDiaryListRequest): Result<FetchShareDiaryListResponse> {
        return shareRepository.fetchDiaryList(request.groupId).map { diaryList ->
            FetchShareDiaryListResponse(diaryList)
        }
    }
}