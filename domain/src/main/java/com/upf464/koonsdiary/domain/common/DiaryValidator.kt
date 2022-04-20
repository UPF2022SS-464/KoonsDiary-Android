package com.upf464.koonsdiary.domain.common

interface DiaryValidator {

    fun validateContent(content: String): Boolean
}
