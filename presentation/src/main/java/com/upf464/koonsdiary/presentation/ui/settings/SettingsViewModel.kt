package com.upf464.koonsdiary.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.presentation.ui.settings.password.PasswordState
import com.upf464.koonsdiary.presentation.ui.settings.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(

) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<SettingsEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _settingsStateFlow = MutableStateFlow(SettingsState())
    val settingsStateFlow = _settingsStateFlow.asStateFlow()

    private val _passwordStateFlow = MutableStateFlow(PasswordState())
    val passwordStateFlow = _passwordStateFlow.asStateFlow()

    private val passwordFlow = MutableStateFlow("")
    private val passwordConfirmFlow = MutableStateFlow("")

    val passwordLengthFlow = combine(passwordStateFlow, passwordFlow, passwordConfirmFlow) { state, password, confirm ->
        if (!state.isConfirm) password.length else confirm.length
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = 0)

    private val _profileStateFlow = MutableStateFlow(ProfileState())
    val profileStateFlow = _profileStateFlow.asStateFlow()

    fun changeUsePassword(flag: Boolean) {
        if (flag) {
            openPasswordDialog()
        } else {
            _settingsStateFlow.value = _settingsStateFlow.value.copy(usePassword = false)
            // TODO: 암호 제거
        }
    }

    fun openPasswordDialog() {
        passwordFlow.value = ""
        passwordConfirmFlow.value = ""
        _passwordStateFlow.value = PasswordState(isShowing = true)
    }

    fun clickPasswordNumber(number: Int) {
        if (!passwordStateFlow.value.isConfirm) {
            passwordFlow.value = passwordFlow.value + number.toString()
        } else {
            passwordConfirmFlow.value = passwordConfirmFlow.value + number.toString()
        }

        if (passwordLengthFlow.value == PASSWORD_MAX_LENGTH) {
            if (!passwordStateFlow.value.isConfirm) {
                _passwordStateFlow.value = _passwordStateFlow.value.copy(isConfirm = true)
            } else if (passwordFlow.value == passwordConfirmFlow.value) {
                // TODO: 암호 설정 완료
                _passwordStateFlow.value = _passwordStateFlow.value.copy(isSuccess = true)
                _settingsStateFlow.value = _settingsStateFlow.value.copy(usePassword = true)
            } else {
                openPasswordDialog()
            }
        }
    }

    fun clearOneNumber() {
        if (!passwordStateFlow.value.isConfirm) {
            passwordFlow.value = passwordFlow.value.dropLast(1)
        } else {
            passwordConfirmFlow.value = passwordConfirmFlow.value.dropLast(1)
        }
    }

    fun closePasswordDialog() {
        _passwordStateFlow.value = _passwordStateFlow.value.copy(isShowing = false)
    }

    fun back() {
        when {
            _passwordStateFlow.value.isShowing -> {
                _passwordStateFlow.value = _passwordStateFlow.value.copy(isShowing = false)
            }
            else -> {
                triggerEvent(SettingsEvent.Finish)
            }
        }
    }

    private fun triggerEvent(event: SettingsEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    companion object {
        const val PASSWORD_MAX_LENGTH = 4
    }
}
