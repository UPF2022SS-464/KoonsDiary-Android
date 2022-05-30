package com.upf464.koonsdiary.presentation.ui.main.share.add_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.usecase.share.AddGroupUseCase
import com.upf464.koonsdiary.presentation.model.share.add_group.AddGroupModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddGroupViewModel @Inject constructor(
    private val addGroupUseCase: AddGroupUseCase
) : ViewModel() {

    val nameFlow = MutableStateFlow("")

    private val _imagePathFlow = MutableStateFlow<String?>(null)
    private val _inviteUserListFlow = MutableStateFlow<List<User>>(listOf())

    private val model = AddGroupModel(
        nameFlow = nameFlow.asStateFlow(),
        imagePathFlow = _imagePathFlow.asStateFlow(),
        inviteUserListUser = _inviteUserListFlow.asStateFlow()
    )

    private val _eventFlow = MutableSharedFlow<AddGroupEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

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
}
