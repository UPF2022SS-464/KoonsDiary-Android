package com.upf464.koonsdiary.presentation.ui.share_diary.diary

import androidx.lifecycle.ViewModel
import com.upf464.koonsdiary.presentation.common.DateTimeUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

internal class ShareDiaryDetailViewModel @Inject constructor(
    val dateTimeUtil: DateTimeUtil
) : ViewModel() {

    private val _diaryStateFlow = MutableStateFlow<ShareDiaryState>(ShareDiaryState.Loading)
    val diaryStateFlow = _diaryStateFlow.asStateFlow()

    private val _commentStateFlow = MutableStateFlow<ShareDiaryCommentState>(ShareDiaryCommentState.Loading)
    val commentStateFlow = _commentStateFlow.asStateFlow()

    fun edit() {

    }

    fun delete() {

    }
}
