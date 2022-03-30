package com.upf464.koonsdiary.domain.common

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GroupValidator @Inject constructor(
) {

    fun isGroupNameValid(name: String): Boolean {
        return name.isNotBlank() && name.length <= 50
    }
}