package com.upf464.koonsdiary.presentation.model.share.add_group

import com.upf464.koonsdiary.domain.model.User
import kotlinx.coroutines.flow.StateFlow

data class AddGroupModel(
    val nameFlow: StateFlow<String>,
    val imagePathFlow: StateFlow<String?>,
    val inviteUserListUser: StateFlow<List<User>>
)
