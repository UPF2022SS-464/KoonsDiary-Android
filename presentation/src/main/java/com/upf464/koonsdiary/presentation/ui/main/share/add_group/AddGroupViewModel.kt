package com.upf464.koonsdiary.presentation.ui.main.share.add_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.usecase.share.AddGroupUseCase
import com.upf464.koonsdiary.domain.usecase.share.SearchUserUseCase
import com.upf464.koonsdiary.presentation.model.share.add_group.AddGroupModel
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
internal class AddGroupViewModel @Inject constructor(
    private val addGroupUseCase: AddGroupUseCase,
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    private val _imagePathFlow = MutableStateFlow<String?>(null)
    private val _inviteUserListFlow = MutableStateFlow<List<User>>(listOf())

    val model = AddGroupModel(
        nameFlow = MutableStateFlow(""),
        imagePathFlow = _imagePathFlow.asStateFlow(),
        inviteUserListUser = _inviteUserListFlow.asStateFlow()
    )

    private val _eventFlow = MutableSharedFlow<AddGroupEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    val keywordFlow = MutableStateFlow("")

    private val _searchResultFlow = MutableStateFlow<SearchUserResultModel>(
        SearchUserResultModel(
            keyword = "",
            userList = listOf()
        )
    )
    val searchResultFlow = _searchResultFlow.asStateFlow()

    private val _waitingResultFlow = MutableStateFlow(false)
    val waitingResultFlow = _waitingResultFlow.asStateFlow()

    init {
        viewModelScope.launch {
            keywordFlow
                .onEach { keyword ->
                    _waitingResultFlow.value = keyword.isNotEmpty()
                }
                .debounce(SEARCH_TIMEOUT)
                .collect { keyword ->
                    if (keyword.isEmpty()) {
                        _searchResultFlow.value = SearchUserResultModel(
                            keyword = "",
                            userList = listOf()
                        )
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
                    _waitingResultFlow.value = false
                }
        }
    }

    fun save() {
        viewModelScope.launch {
            addGroupUseCase(
                AddGroupUseCase.Request(
                    name = model.nameFlow.value,
                    imagePath = model.imagePathFlow.value,
                    inviteUserIdList = model.inviteUserListUser.value.map { it.id }
                )
            ).onSuccess { response ->
                _eventFlow.tryEmit(AddGroupEvent.NavigateToGroup(response.groupId))
            }.onFailure {
                // TODO("오류 처리")
            }
        }
    }

    fun setGroupImage(imagePath: String) {
        _imagePathFlow.value = imagePath
    }

    fun resetGroupImage() {
        _imagePathFlow.value = null
    }

    fun deleteInviteUserOn(index: Int) {
        val userList = _inviteUserListFlow.value
        _inviteUserListFlow.value = userList.subList(0, index) + userList.subList(index + 1, userList.size)
    }

    fun addInviteUser(user: User) {
        val userList = _inviteUserListFlow.value
        _inviteUserListFlow.value = listOf(user) + userList
    }

    companion object {
        private const val SEARCH_TIMEOUT = 1000L
    }
}
