package com.upf464.koonsdiary.presentation.ui.share_diary.settings

import com.upf464.koonsdiary.domain.model.ShareGroup

sealed class ShareGroupState {

    object Loading : ShareGroupState()

    data class Success(
        val group: ShareGroup
    ) : ShareGroupState()
}
