package com.upf464.koonsdiary.presentation.ui.share_diary.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.usecase.share.FetchGroupUseCase
import com.upf464.koonsdiary.domain.usecase.share.InviteUserUseCase
import com.upf464.koonsdiary.domain.usecase.share.SearchUserUseCase
import com.upf464.koonsdiary.domain.usecase.share.UpdateGroupUseCase
import com.upf464.koonsdiary.domain.usecase.user.UpdateUserUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ShareGroupSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchGroupUseCase: FetchGroupUseCase,
    private val searchUserUseCase: SearchUserUseCase,
    private val inviteUserUseCase: InviteUserUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val groupId = savedStateHandle.get<String>(Constants.PARAM_GROUP_ID)?.toIntOrNull() ?: 0
    private lateinit var group: ShareGroup

    // TODO: 내 정보 받아와서 표시
    private val _meFlow = MutableStateFlow(User())
    val meFlow = _meFlow.asStateFlow()

    val groupNameFlow = MutableStateFlow("")
    val nicknameFlow = MutableStateFlow("")

    private val _imagePathFlow = MutableStateFlow("")
    val imagePathFlow = _imagePathFlow.asStateFlow()

    private val _groupStateFlow = MutableStateFlow<ShareGroupState>(ShareGroupState.Loading)
    val groupStateFlow = _groupStateFlow.asStateFlow()

    private val _inviteUserListFlow = MutableStateFlow<List<User>>(listOf())
    val inviteUserListFlow = _inviteUserListFlow.asStateFlow()

    val keywordFlow = MutableStateFlow("")

    private val _searchWaitingFlow = MutableStateFlow(false)
    val searchWaitingFlow = _searchWaitingFlow.asStateFlow()

    private val _searchResultFlow = MutableStateFlow<List<User>>(listOf())
    val searchResultFlow = _searchResultFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ShareGroupSettingsEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _groupNameStateFlow = MutableStateFlow<ShareGroupDialogState>(ShareGroupDialogState.Closed)
    val groupNameStateFlow = _groupNameStateFlow.asStateFlow()

    private val _nicknameStateFlow = MutableStateFlow<ShareGroupDialogState>(ShareGroupDialogState.Closed)
    val nicknameStateFlow = _nicknameStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            fetchGroupUseCase(FetchGroupUseCase.Request(groupId))
                .onSuccess { response ->
                    group = response.group
                    _groupStateFlow.value = ShareGroupState.Success(group)

                }
                .onFailure {
                    // TODO: 오류 처리
                }
        }
    }

    fun openGroupNameDialog() {
        groupNameFlow.value = group.name
        _groupNameStateFlow.value = ShareGroupDialogState.Opened
    }

    fun saveGroupName() {
        _groupNameStateFlow.value = ShareGroupDialogState.Loading
        if (groupNameFlow.value != group.name) {
            viewModelScope.launch {
                updateGroupUseCase(
                    UpdateGroupUseCase.Request(
                        groupId = groupId,
                        name = groupNameFlow.value,
                        imagePath = group.imagePath
                    )
                ).onSuccess {
                    group = group.copy(name = groupNameFlow.value)
                    _groupStateFlow.value = ShareGroupState.Success(group)
                }.onFailure {
                    triggerEvent(ShareGroupSettingsEvent.SaveGroupNameFailed)
                }
                closeGroupNameDialog()
            }
        } else {
            closeGroupNameDialog()
        }
    }

    fun closeGroupNameDialog() {
        _groupNameStateFlow.value = ShareGroupDialogState.Closed
    }

    fun openNicknameDialog() {
        nicknameFlow.value = meFlow.value.nickname
        _nicknameStateFlow.value = ShareGroupDialogState.Opened
    }

    fun saveNickname() {
        _nicknameStateFlow.value = ShareGroupDialogState.Loading
        if (nicknameFlow.value != meFlow.value.nickname) {
            viewModelScope.launch {
                updateUserUseCase(
                    UpdateUserUseCase.Request(
                        nickname = nicknameFlow.value
                    )
                ).onSuccess {
                    _meFlow.value = _meFlow.value.copy(nickname = nicknameFlow.value)
                }.onFailure {
                    triggerEvent(ShareGroupSettingsEvent.SaveNicknameFailed)
                }
                closeNicknameDialog()
            }
        } else {
            closeNicknameDialog()
        }
    }

    fun closeNicknameDialog() {
        _nicknameStateFlow.value = ShareGroupDialogState.Closed
    }

    fun triggerEvent(event: ShareGroupSettingsEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}
