package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.InviteUserRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class InviteUserUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<InviteUserRequest, EmptyResponse> {

    override suspend fun invoke(request: InviteUserRequest): Result<EmptyResponse> {
        if (request.userIdList.isEmpty()) {
            return Result.failure(ShareError.EmptyContent)
        }

        return shareRepository.inviteUser(request.groupId, request.userIdList).map {
            EmptyResponse
        }
    }
}