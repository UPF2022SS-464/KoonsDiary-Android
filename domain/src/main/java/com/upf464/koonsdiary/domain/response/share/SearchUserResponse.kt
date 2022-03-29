package com.upf464.koonsdiary.domain.response.share

import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.response.Response

data class SearchUserResponse(
    val userList: List<User>
) : Response