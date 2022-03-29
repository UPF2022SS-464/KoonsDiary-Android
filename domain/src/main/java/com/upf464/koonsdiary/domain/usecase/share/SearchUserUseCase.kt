package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.SearchUserRequest
import com.upf464.koonsdiary.domain.response.share.SearchUserResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SearchUserUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) : ResultUseCase<SearchUserRequest, SearchUserResponse> {

    override suspend fun invoke(request: SearchUserRequest): Result<SearchUserResponse> {
        if (request.keyword.isBlank()) {
            return Result.failure(ShareError.EmptyContent)
        }

        return shareRepository.searchUser(request.keyword).map { userList ->
            SearchUserResponse(userList)
        }
    }
}