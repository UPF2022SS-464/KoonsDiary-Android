package com.upf464.koonsdiary.presentation.ui.main.share.add_group

import com.upf464.koonsdiary.domain.model.User

sealed class AddGroupSearchState {

    abstract val keyword: String

    data class Loading(
        override val keyword: String
    ) : AddGroupSearchState()

    data class Success(
        override val keyword: String,
        val userList: List<User>
    ) : AddGroupSearchState()
}
