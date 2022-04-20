package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        if (request.keyword.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        return shareRepository.searchUser(request.keyword).map { userList ->
            Response(userList)
        }
    }

    data class Request(
        val keyword: String
    )

    data class Response(
        val userList: List<User>
    )
}
