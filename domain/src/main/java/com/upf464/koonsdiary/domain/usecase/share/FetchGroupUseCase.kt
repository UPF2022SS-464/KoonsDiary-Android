package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class FetchGroupUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        return shareRepository.fetchGroup(request.groupId).map { group ->
            Response(group)
        }
    }

    data class Request(
        val groupId: Int
    )

    data class Response(
        val group: ShareGroup
    )
}
