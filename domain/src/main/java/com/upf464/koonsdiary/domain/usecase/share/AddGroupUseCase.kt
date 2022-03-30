package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.AddGroupRequest
import com.upf464.koonsdiary.domain.response.share.AddGroupResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AddGroupUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<AddGroupRequest, AddGroupResponse> {

    override suspend fun invoke(request: AddGroupRequest): Result<AddGroupResponse> {
        if (request.name.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        val group = ShareGroup(
            name = request.name,
            imagePath = request.imagePath
        )

        return shareRepository.addGroup(group, request.inviteUserIdList).map { groupId ->
            AddGroupResponse(groupId)
        }
    }
}