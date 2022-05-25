package com.upf464.koonsdiary.presentation.ui.main.share.group_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.usecase.share.FetchGroupListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ShareGroupListViewModel @Inject constructor(
    private val fetchGroupListUseCase: FetchGroupListUseCase
) : ViewModel() {

    private val _groupListStateFlow = MutableStateFlow<ShareGroupListState>(ShareGroupListState.Loading)
    val groupListStateFlow = _groupListStateFlow.asStateFlow()

    private val _viewTypeFlow = MutableStateFlow(ShareGroupListViewType.PAGER)
    val viewTypeFlow = _viewTypeFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ShareGroupListEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            fetchGroupListUseCase().onSuccess { response ->
                _groupListStateFlow.value = ShareGroupListState.Success(response.groupList)
            }
        }
    }

    fun changeViewType(viewType: ShareGroupListViewType) {
        _viewTypeFlow.value = viewType
    }

    fun navigateToGroup(group: ShareGroup?) {
        if (group == null) {
            _eventFlow.tryEmit(ShareGroupListEvent.NavigateToAddGroup)
        } else {
            _eventFlow.tryEmit(ShareGroupListEvent.NavigateToGroup(groupId = group.id))
        }
    }
}
