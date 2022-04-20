package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class KickUserUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return shareRepository.kickUser(request.groupId, request.userId)
    }

    data class Request(
        val groupId: Int,
        val userId: Int
    )
}
