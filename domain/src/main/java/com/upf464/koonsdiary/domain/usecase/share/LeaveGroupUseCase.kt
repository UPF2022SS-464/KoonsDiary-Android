package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.LeaveGroupRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class LeaveGroupUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<LeaveGroupRequest, EmptyResponse> {

    override suspend fun invoke(request: LeaveGroupRequest): Result<EmptyResponse> {
        return shareRepository.leaveGroup(request.groupId).map {
            EmptyResponse
        }
    }
}