package com.upf464.koonsdiary.presentation.ui.main.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor() : ViewModel() {

    private val _eventFlow = MutableSharedFlow<NotificationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun openSettings() {
        triggerEvent(NotificationEvent.OpenSettings)
    }

    private fun triggerEvent(event: NotificationEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}
