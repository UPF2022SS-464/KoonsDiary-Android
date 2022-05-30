package com.upf464.koonsdiary.presentation.model.share.add_group

import com.upf464.koonsdiary.domain.model.User

data class SearchUserResultModel(
    val keyword: String,
    val userList: List<User>
)
