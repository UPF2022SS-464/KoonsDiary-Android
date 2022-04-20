package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class FetchGroupListUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(): Result<Response> {
        return shareRepository.fetchGroupList().map { groupList ->
            Response(groupList)
        }
    }

    data class Response(
        val groupList: List<ShareGroup>
    )
}
