package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.FetchUserImageListRequest
import com.upf464.koonsdiary.domain.response.user.FetchUserImageListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchUserImageListUseCase @Inject constructor(
    private val userRepository: UserRepository
) : ResultUseCase<FetchUserImageListRequest, FetchUserImageListResponse> {

    override suspend fun invoke(request: FetchUserImageListRequest): Result<FetchUserImageListResponse> {
        return userRepository.fetchUserImageList().map { imageList ->
            FetchUserImageListResponse(imageList)
        }
    }
}