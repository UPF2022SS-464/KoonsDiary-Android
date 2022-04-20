package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.common.GroupValidator
import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val shareRepository: ShareRepository,
    private val validator: GroupValidator
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        if (!validator.isGroupNameValid(request.name)) {
            return Result.failure(ShareError.InvalidGroupName)
        }

        val group = ShareGroup(
            name = request.name,
            imagePath = request.imagePath
        )

        return shareRepository.addGroup(group, request.inviteUserIdList).map { groupId ->
            Response(groupId)
        }
    }

    data class Request(
        val name: String,
        val imagePath: String?,
        val inviteUserIdList: List<Int>
    )

    data class Response(
        val groupId: Int
    )
}
