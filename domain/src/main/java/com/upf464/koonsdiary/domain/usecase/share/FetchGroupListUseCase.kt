package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.FetchGroupListRequest
import com.upf464.koonsdiary.domain.response.share.FetchGroupListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchGroupListUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<FetchGroupListRequest, FetchGroupListResponse> {

    override suspend fun invoke(request: FetchGroupListRequest): Result<FetchGroupListResponse> {
        return shareRepository.fetchGroupList().map { groupList ->
            FetchGroupListResponse(groupList)
        }
    }
}