package com.upf464.koonsdiary.presentation.ui.share_diary.settings

sealed class ShareGroupSettingsEvent {
    object SaveGroupNameFailed : ShareGroupSettingsEvent()
    object SaveNicknameFailed : ShareGroupSettingsEvent()
    object InviteUserSuccess : ShareGroupSettingsEvent()
}
