package com.upf464.koonsdiary.data.model

sealed class MessageData {

    data class TokenChanged(val token: String) : MessageData()
}
