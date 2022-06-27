package com.upf464.koonsdiary.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.domain.usecase.security.AuthenticateWithBiometricUseCase
import com.upf464.koonsdiary.domain.usecase.security.DeletePINUseCase
import com.upf464.koonsdiary.domain.usecase.security.SavePINUseCase
import com.upf464.koonsdiary.domain.usecase.security.SetBiometricUseCase
import com.upf464.koonsdiary.domain.usecase.user.FetchUserImageListUseCase
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
    private val fetchUserImageListUseCase: FetchUserImageListUseCase,
    private val biometricUseCase: AuthenticateWithBiometricUseCase,
    private val setBiometricUseCase: SetBiometricUseCase,
    private val savePINUseCase: SavePINUseCase,
    private val deletePINUseCase: DeletePINUseCase,
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

    val nicknameFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            fetchUserImageListUseCase().onSuccess { response ->
                _profileStateFlow.value = ProfileState(imageList = response.imageList)
            }
        }
    }

    fun changeUsePassword(flag: Boolean) {
        if (flag) {
            openPasswordDialog()
        } else {
            viewModelScope.launch {
                deletePINUseCase().onSuccess {
                    _settingsStateFlow.value = _settingsStateFlow.value.copy(usePassword = false)
                }
            }
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
                viewModelScope.launch {
                    savePINUseCase(SavePINUseCase.Request(passwordFlow.value))
                        .onSuccess {
                            _passwordStateFlow.value = _passwordStateFlow.value.copy(isSuccess = true)
                            _settingsStateFlow.value = _settingsStateFlow.value.copy(usePassword = true)
                        }
                }
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

    fun openProfileScreen() {
        _profileStateFlow.value = _profileStateFlow.value.copy(isShowing = true, selectedIndex = -1)
    }

    fun selectImageAt(index: Int) {
        _profileStateFlow.value = _profileStateFlow.value.copy(selectedIndex = index)
    }

    fun confirmImage() {
        if (_profileStateFlow.value.selectedIndex != -1) {
            // TODO: 프로필 이미지 변경
            _profileStateFlow.value = _profileStateFlow.value.copy(isShowing = false)
            _settingsStateFlow.value =
                _settingsStateFlow.value.copy(userImage = _profileStateFlow.value.imageList[_profileStateFlow.value.selectedIndex])
        }
    }

    fun openNicknameDialog() {
        nicknameFlow.value = ""
        _settingsStateFlow.value = _settingsStateFlow.value.copy(isEditingNickname = true)
    }

    fun confirmNickname() {
        if (nicknameFlow.value.isNotEmpty()) {
            // TODO: 닉네임 변경
            _settingsStateFlow.value = _settingsStateFlow.value.copy(isEditingNickname = false, nickname = nicknameFlow.value)
        }
    }

    fun closeNicknameDialog() {
        _settingsStateFlow.value = _settingsStateFlow.value.copy(isEditingNickname = false)
    }

    fun changeUseBiometric(flag: Boolean) {
        if (flag) {
            viewModelScope.launch {
                biometricUseCase().flatMap {
                    setBiometricUseCase(SetBiometricUseCase.Request(true))
                }.onSuccess {
                    _settingsStateFlow.value = _settingsStateFlow.value.copy(useBiometric = true)
                }.onFailure { error ->
                    when (error) {
                        SecurityError.AuthenticateFailed -> triggerEvent(SettingsEvent.ShowBiometricFailedError)
                        SecurityError.HardwareUnavailable -> triggerEvent(SettingsEvent.ShowBiometricUnavailableError)
                        SecurityError.Lockout -> triggerEvent(SettingsEvent.ShowLockBiometricError)
                        SecurityError.NoCredential -> triggerEvent(SettingsEvent.ShowNoBiometricError)
                        SecurityError.Timeout -> triggerEvent(SettingsEvent.ShowBiometricTimeoutError)
                    }
                }
            }
        } else {
            viewModelScope.launch {
                setBiometricUseCase(SetBiometricUseCase.Request(false)).onSuccess {
                    _settingsStateFlow.value = _settingsStateFlow.value.copy(useBiometric = false)
                }
            }
        }
    }

    fun back() {
        when {
            _passwordStateFlow.value.isShowing -> {
                _passwordStateFlow.value = _passwordStateFlow.value.copy(isShowing = false)
            }
            _profileStateFlow.value.isShowing -> {
                _profileStateFlow.value = _profileStateFlow.value.copy(isShowing = false)
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
