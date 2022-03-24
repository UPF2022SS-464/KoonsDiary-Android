package com.upf464.koonsdiary.domain.common

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DiaryValidator @Inject constructor() {

    fun validateContent(content: String): Boolean {
        return content.isNotBlank()
    }
}