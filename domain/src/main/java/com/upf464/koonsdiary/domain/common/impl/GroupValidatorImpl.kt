package com.upf464.koonsdiary.domain.common.impl

import com.upf464.koonsdiary.domain.common.GroupValidator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GroupValidatorImpl @Inject constructor() : GroupValidator {

    override fun isGroupNameValid(name: String): Boolean {
        return name.isNotBlank() && name.length <= 50
    }
}
