package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

class FetchUserImageListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<Response> {
        return userRepository.fetchUserImageList().map { imageList ->
            Response(imageList)
        }
    }

    data class Response(
        val imageList: List<User.Image>
    )
}
