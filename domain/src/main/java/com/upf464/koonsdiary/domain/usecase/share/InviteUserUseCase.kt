package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class InviteUserUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        if (request.userIdList.isEmpty()) {
            return Result.failure(ShareError.EmptyContent)
        }

        return shareRepository.inviteUser(request.groupId, request.userIdList)
    }

    data class Request(
        val groupId: Int,
        val userIdList: List<Int>
    )
}
