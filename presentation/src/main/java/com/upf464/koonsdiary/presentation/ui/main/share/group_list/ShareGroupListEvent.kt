package com.upf464.koonsdiary.presentation.ui.main.share.group_list

sealed class ShareGroupListEvent {
    object NavigateToAddGroup : ShareGroupListEvent()
    data class NavigateToGroup(val groupId: Int) : ShareGroupListEvent()
}
