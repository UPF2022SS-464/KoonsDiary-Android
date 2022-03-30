package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.DeleteGroupRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class DeleteGroupUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<DeleteGroupRequest, EmptyResponse> {

    override suspend fun invoke(request: DeleteGroupRequest): Result<EmptyResponse> {
        return shareRepository.deleteGroup(request.groupId).map {
            EmptyResponse
        }
    }
}