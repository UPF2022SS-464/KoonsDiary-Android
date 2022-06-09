package com.upf464.koonsdiary.presentation.ui.share_diary.settings

import androidx.lifecycle.ViewModel
import com.upf464.koonsdiary.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class ShareGroupSettingsViewModel @Inject constructor(

) : ViewModel() {

    val groupNameFlow = MutableStateFlow("")

    private val _imagePathFlow = MutableStateFlow("")
    val imagePathFlow = _imagePathFlow.asStateFlow()

    private val _groupState = MutableStateFlow<ShareGroupState>(ShareGroupState.Loading)
    val groupState = _groupState.asStateFlow()

    private val _inviteUserListFlow = MutableStateFlow<List<User>>(listOf())
    val inviteUserListFlow = _inviteUserListFlow.asStateFlow()

    val keywordFlow = MutableStateFlow("")

    private val _searchWaitingFlow = MutableStateFlow(false)
    val searchWaitingFlow = _searchWaitingFlow.asStateFlow()

    private val _searchResultFlow = MutableStateFlow<List<User>>(listOf())
    val searchResultFlow = _searchResultFlow.asStateFlow()
}
