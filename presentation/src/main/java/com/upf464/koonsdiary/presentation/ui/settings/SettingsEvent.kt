package com.upf464.koonsdiary.presentation.ui.settings

sealed interface SettingsEvent {

    object Finish : SettingsEvent
    object ShowBiometricFailedError : SettingsEvent
    object ShowBiometricUnavailableError : SettingsEvent
    object ShowLockBiometricError : SettingsEvent
    object ShowNoBiometricError : SettingsEvent
    object ShowBiometricTimeoutError : SettingsEvent
}
