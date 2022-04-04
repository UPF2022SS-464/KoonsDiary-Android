package com.upf464.koonsdiary.domain.common

internal interface DiaryValidator {

    fun validateContent(content: String): Boolean
}