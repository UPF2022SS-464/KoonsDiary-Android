package com.upf464.koonsdiary.local.source

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.upf464.koonsdiary.data.error.SecurityErrorData
import com.upf464.koonsdiary.data.model.LockTypeData
import com.upf464.koonsdiary.data.source.SecurityLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

internal class SecurityLocalDataSourceImpl @Inject constructor(
    private val pref: SharedPreferences,
    @ApplicationContext private val context: Context
) : SecurityLocalDataSource {

    override suspend fun setPIN(pin: String): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            pref.edit {
                putString(KEY_PIN, pin)
            }
        }
    }

    override suspend fun getPIN(): Result<String> = runCatching {
        withContext(Dispatchers.IO) {
            pref.getString(KEY_PIN, null) ?: throw SecurityErrorData.NoStoredPIN
        }
    }

    override suspend fun getDisposableSalt(): Result<String> = runCatching {
        withContext(Dispatchers.IO) {
            pref.getString(KEY_UUID, null)
                ?: UUID.randomUUID().toString().also { uuid ->
                    pref.edit {
                        putString(KEY_UUID, uuid)
                    }
                }
        }
    }

    override suspend fun clearPIN(): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            pref.edit {
                remove(KEY_PIN)
                remove(KEY_UUID)
                remove(KEY_BIOMETRIC)
            }
        }
    }

    override suspend fun setBiometric(isActive: Boolean): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            if (isActive && pref.getString(KEY_PIN, null) == null) {
                throw SecurityErrorData.NoStoredPIN
            }

            pref.edit {
                putBoolean(KEY_BIOMETRIC, isActive)
            }
        }
    }

    override suspend fun fetchLockType(): Result<LockTypeData> = runCatching {
        withContext(Dispatchers.IO) {
            val pin = pref.getString(KEY_PIN, null)
            val biometric = pref.getBoolean(KEY_BIOMETRIC, false)

            if (pin != null) {
                if (biometric) LockTypeData.BIOMETRIC
                else LockTypeData.PIN
            } else LockTypeData.NONE
        }
    }

    companion object {
        private const val KEY_PIN = "KEY_PIN"
        private const val KEY_UUID = "KEY_UUID"
        private const val KEY_BIOMETRIC = "KEY_UUID"
    }
}