package com.upf464.koonsdiary.presentation.ui.main.diary.add

sealed class AddDiaryEvent {

    data class Success(val diaryId: Int) : AddDiaryEvent()
}
