package com.upf464.koonsdiary.domain.common

import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.experimental.and

@Singleton
internal class HashGenerator @Inject constructor(
    private val digest: MessageDigest
) {

    fun hashPasswordWithSalt(username: String, password: String): String {
        val passwordWithSalt = password + hash(username)
        return hash(passwordWithSalt)
    }

    private fun hash(plain: String): String = byteArrayToString(digest.digest(plain.toByteArray()))

    private fun byteArrayToString(byteArray: ByteArray) =
        byteArray.joinToString("") { byte ->
            (byte.and(0xff.toByte()) + 0x100)
                .toString(16)
                .takeLast(2)
        }
}