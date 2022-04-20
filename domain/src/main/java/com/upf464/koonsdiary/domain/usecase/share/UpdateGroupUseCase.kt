package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.common.GroupValidator
import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class UpdateGroupUseCase @Inject constructor(
    private val shareRepository: ShareRepository,
    private val validator: GroupValidator
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        if (!validator.isGroupNameValid(request.name)) {
            return Result.failure(ShareError.InvalidGroupName)
        }

        val group = ShareGroup(
            id = request.groupId,
            name = request.name,
            imagePath = request.imagePath
        )

        return shareRepository.updateGroup(group).map { groupId ->
            Response(groupId)
        }
    }

    data class Request(
        val groupId: Int,
        val name: String,
        val imagePath: String?
    )

    data class Response(
        val groupId: Int
    )
}
