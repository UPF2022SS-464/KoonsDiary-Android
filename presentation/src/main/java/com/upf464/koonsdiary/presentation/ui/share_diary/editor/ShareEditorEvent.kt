package com.upf464.koonsdiary.presentation.ui.share_diary.editor

sealed class ShareEditorEvent {

    data class AddSuccess(
        val diaryId: Int
    ) : ShareEditorEvent()

    data class EditSuccess(
        val diaryId: Int
    ) : ShareEditorEvent()
}
