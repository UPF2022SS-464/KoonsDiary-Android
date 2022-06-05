package com.upf464.koonsdiary.presentation.ui.share_diary.diary

import com.upf464.koonsdiary.domain.model.ShareDiary

sealed class ShareDiaryState {

    object Loading : ShareDiaryState()

    data class Success(
        val diary: ShareDiary
    ) : ShareDiaryState()
}
