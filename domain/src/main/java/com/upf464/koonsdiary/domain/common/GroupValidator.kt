package com.upf464.koonsdiary.domain.common

internal interface GroupValidator {

    fun isGroupNameValid(name: String): Boolean
}