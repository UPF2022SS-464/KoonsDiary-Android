package com.upf464.koonsdiary.presentation.ui.main.share.add_group

sealed class AddGroupEvent {

    data class NavigateToGroup(val groupId: Int) : AddGroupEvent()
}
