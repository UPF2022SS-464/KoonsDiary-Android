package com.upf464.koonsdiary.domain.response.user

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.response.Response

data class FetchUserImageListResponse(
    val imageList: List<User.Image>
) : Response