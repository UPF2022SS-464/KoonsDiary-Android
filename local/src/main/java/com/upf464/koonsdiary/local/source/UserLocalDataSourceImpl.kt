package com.upf464.koonsdiary.local.source

import android.content.SharedPreferences
import androidx.core.content.edit
import com.upf464.koonsdiary.data.source.UserLocalDataSource
import com.upf464.koonsdiary.local.error.SignInLocalError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UserLocalDataSourceImpl @Inject constructor(
    private val pref: SharedPreferences
) : UserLocalDataSource {

    override suspend fun getAutoSignInToken(): Result<String> = runCatching {
        withContext(Dispatchers.IO) {
            pref.getString(KEY_SIGN_IN_TYPE, null) ?: throw SignInLocalError.NoToken
        }
    }

    override suspend fun setAutoSignIn(type: String, token: String?): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            pref.edit {
                putString(KEY_SIGN_IN_TYPE, type)
                token?.let {
                    putString(KEY_SIGN_IN_TOKEN, token)
                }
            }
        }
    }

    override suspend fun getAutoSignInType(): Result<String?> = runCatching {
        withContext(Dispatchers.IO) {
            pref.getString(KEY_SIGN_IN_TYPE, null)
        }
    }

    override suspend fun clearAutoSignIn(): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            pref.edit {
                remove(KEY_SIGN_IN_TYPE)
                remove(KEY_SIGN_IN_TOKEN)
            }
        }
    }

    companion object {
        private const val KEY_SIGN_IN_TYPE = "KEY_SIGN_IN_TYPE"
        private const val KEY_SIGN_IN_TOKEN = "KEY_SIGN_IN_TOKEN"
    }
}
