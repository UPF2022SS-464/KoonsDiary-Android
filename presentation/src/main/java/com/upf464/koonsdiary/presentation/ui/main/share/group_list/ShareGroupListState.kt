package com.upf464.koonsdiary.presentation.ui.main.share.group_list

import com.upf464.koonsdiary.domain.model.ShareGroup

sealed class ShareGroupListState {
    object Loading : ShareGroupListState()
    data class Success(val groupList: List<ShareGroup>) : ShareGroupListState()
}
