package com.upf464.koonsdiary.presentation.ui.main.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.usecase.notification.FetchNotificationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val fetchNotificationListUseCase: FetchNotificationListUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<NotificationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _stateFlow = MutableStateFlow(NotificationState())
    val stateFlow = _stateFlow.asStateFlow()

    fun init() {
        viewModelScope.launch {
            fetchNotificationListUseCase().onSuccess { response ->
                _stateFlow.value = _stateFlow.value.copy(notificationList = response.notificationList)
            }
        }
    }

    fun openSettings() {
        triggerEvent(NotificationEvent.OpenSettings)
    }

    private fun triggerEvent(event: NotificationEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}
