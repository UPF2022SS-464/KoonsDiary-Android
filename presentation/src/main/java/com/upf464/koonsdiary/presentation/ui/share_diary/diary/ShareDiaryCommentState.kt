package com.upf464.koonsdiary.presentation.ui.share_diary.diary

import com.upf464.koonsdiary.domain.model.Comment

sealed class ShareDiaryCommentState {

    object Loading : ShareDiaryCommentState()

    data class Success(
        val commentList: List<Comment>
    ) : ShareDiaryCommentState()
}
