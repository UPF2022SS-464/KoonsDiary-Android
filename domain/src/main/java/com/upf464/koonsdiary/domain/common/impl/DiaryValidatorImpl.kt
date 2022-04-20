package com.upf464.koonsdiary.domain.common.impl

import com.upf464.koonsdiary.domain.common.DiaryValidator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DiaryValidatorImpl @Inject constructor() : DiaryValidator {

    override fun validateContent(content: String): Boolean {
        return content.isNotBlank()
    }
}
