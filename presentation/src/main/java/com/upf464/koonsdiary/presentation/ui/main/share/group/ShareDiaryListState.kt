package com.upf464.koonsdiary.presentation.ui.main.share.group

import com.upf464.koonsdiary.domain.model.ShareDiary

sealed class ShareDiaryListState {

    object Loading : ShareDiaryListState()

    data class Success(
        val diaryList: List<ShareDiary>
    ) : ShareDiaryListState()
}
