package com.upf464.koonsdiary.presentation.ui.share_diary.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.usecase.share.DeleteGroupUseCase
import com.upf464.koonsdiary.domain.usecase.share.FetchGroupUseCase
import com.upf464.koonsdiary.domain.usecase.share.InviteUserUseCase
import com.upf464.koonsdiary.domain.usecase.share.KickUserUseCase
import com.upf464.koonsdiary.domain.usecase.share.SearchUserUseCase
import com.upf464.koonsdiary.domain.usecase.share.UpdateGroupUseCase
import com.upf464.koonsdiary.domain.usecase.user.UpdateUserUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.model.share.add_group.SearchUserResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
internal class ShareGroupSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchGroupUseCase: FetchGroupUseCase,
    private val searchUserUseCase: SearchUserUseCase,
    private val inviteUserUseCase: InviteUserUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val kickUserUseCase: KickUserUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase
) : ViewModel() {

    private val groupId = savedStateHandle.get<String>(Constants.PARAM_GROUP_ID)?.toIntOrNull() ?: 0
    private lateinit var group: ShareGroup

    // TODO: 내 정보 받아와서 표시
    private val _meFlow = MutableStateFlow(User())
    val meFlow = _meFlow.asStateFlow()

    val groupNameFlow = MutableStateFlow("")
    val nicknameFlow = MutableStateFlow("")

    private val _groupStateFlow = MutableStateFlow<ShareGroupState>(ShareGroupState.Loading)
    val groupStateFlow = _groupStateFlow.asStateFlow()

    private val _inviteUserListFlow = MutableStateFlow<List<User>>(listOf())
    val inviteUserListFlow = _inviteUserListFlow.asStateFlow()

    val keywordFlow = MutableStateFlow("")

    private val _searchWaitingFlow = MutableStateFlow(false)
    val searchWaitingFlow = _searchWaitingFlow.asStateFlow()

    private val _searchResultFlow = MutableStateFlow(SearchUserResultModel("", listOf()))
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

        viewModelScope.launch {
            keywordFlow
                .onEach { keyword ->
                    _searchWaitingFlow.value = keyword.isNotEmpty()
                }
                .debounce(SEARCH_TIMEOUT)
                .collect { keyword ->
                    if (keyword.isEmpty()) {
                        _searchResultFlow.value = SearchUserResultModel(keyword, listOf())
                    } else {
                        searchUserUseCase(
                            SearchUserUseCase.Request(keyword = keyword)
                        ).onSuccess { response ->
                            _searchResultFlow.value = SearchUserResultModel(
                                keyword = keyword,
                                userList = response.userList
                            )
                        }.onFailure {
                            // TODO("오류 처리")
                        }
                    }
                    _searchWaitingFlow.value = false
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

    fun setImage(imagePath: String?) {
        viewModelScope.launch {
            updateGroupUseCase(
                UpdateGroupUseCase.Request(
                    groupId = groupId,
                    name = group.name,
                    imagePath = imagePath
                )
            ).onSuccess {
                group = group.copy(imagePath = imagePath)
                _groupStateFlow.value = ShareGroupState.Success(group)
            }.onFailure {
                triggerEvent(ShareGroupSettingsEvent.SaveGroupImageFailed)
            }
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

    fun deleteInviteUserOn(index: Int) {
        val userList = _inviteUserListFlow.value
        _inviteUserListFlow.value = userList.subList(0, index) + userList.subList(index + 1, userList.size)
    }

    fun addInviteUser(user: User) {
        val userList = _inviteUserListFlow.value
        _inviteUserListFlow.value = listOf(user) + userList
    }

    fun removeResignedUser(user: User) {
        // TODO: 탈퇴 멤버 정보 삭제
        // TODO: 실시간 목록 반영
    }

    fun inviteUserList() {
        viewModelScope.launch {
            inviteUserUseCase(
                InviteUserUseCase.Request(
                    groupId = groupId,
                    userIdList = inviteUserListFlow.value.map { it.id }
                )
            ).onSuccess {
                _eventFlow.tryEmit(ShareGroupSettingsEvent.InviteUserSuccess)
            }.onFailure {
                // TODO: 오류 처리
            }
        }
    }

    fun kickUser(index: Int) {
        viewModelScope.launch {
            kickUserUseCase(
                KickUserUseCase.Request(
                    groupId = groupId,
                    userId = group.userList[index].id
                )
            ).onSuccess {
                // TODO: 실시간 목록 반영
            }.onFailure {
                // TODO: 오류 처리
            }
        }
    }

    fun deleteGroup() {
        viewModelScope.launch {
            deleteGroupUseCase(
                DeleteGroupUseCase.Request(groupId)
            ).onSuccess {
                _eventFlow.tryEmit(ShareGroupSettingsEvent.DeleteGroupSuccess)
            }.onFailure {
                // TODO: 오류 처리
            }
        }
    }

    companion object {
        private const val SEARCH_TIMEOUT = 1000L
    }
}
