package com.upf464.koonsdiary.presentation.ui.main.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.Notification
import com.upf464.koonsdiary.domain.usecase.notification.FetchNotificationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val fetchNotificationListUseCase: FetchNotificationListUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<NotificationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _stateFlow = MutableStateFlow(NotificationState())
    val stateFlow = _stateFlow.asStateFlow()

    fun init() {
        viewModelScope.launch {
            fetchNotificationListUseCase().onSuccess { response ->
                _stateFlow.update {
                    it.copy(
                        isLoading = false,
                        notificationList = response.notificationList,
                    )
                }
            }
        }
    }

    fun openSettings() {
        triggerEvent(NotificationEvent.OpenSettings)
    }

    fun acceptGroupInvite(groupId: Int) {
        TODO("Not yet implemented")
    }

    fun rejectGroupInvite(groupId: Int) {
        TODO("Not yet implemented")
    }

    fun clickNotification(position: Int) {
        when (val notification = stateFlow.value.notificationList[position]) {
            is Notification.CottonReaction -> {}
            is Notification.DiaryComment -> {
                triggerEvent(NotificationEvent.NavigateToShareDiary(notification.diary.id))
            }
            is Notification.NewDiary -> {
                triggerEvent(NotificationEvent.NavigateToShareDiary(notification.diary.id))
            }
            is Notification.GroupInvite -> {}
        }
    }

    private fun triggerEvent(event: NotificationEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}
