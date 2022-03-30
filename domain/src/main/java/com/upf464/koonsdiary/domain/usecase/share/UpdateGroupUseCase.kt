package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.common.GroupValidator
import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.UpdateGroupRequest
import com.upf464.koonsdiary.domain.response.share.UpdateGroupResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class UpdateGroupUseCase @Inject constructor(
    private val shareRepository: ShareRepository,
    private val validator: GroupValidator
) : ResultUseCase<UpdateGroupRequest, UpdateGroupResponse> {

    override suspend fun invoke(request: UpdateGroupRequest): Result<UpdateGroupResponse> {
        if (!validator.isGroupNameValid(request.name)) {
            return Result.failure(ShareError.InvalidGroupName)
        }

        val group = ShareGroup(
            id = request.groupId,
            name = request.name,
            imagePath = request.imagePath
        )

        return shareRepository.updateGroup(group).map { groupId ->
            UpdateGroupResponse(groupId)
        }
    }
}