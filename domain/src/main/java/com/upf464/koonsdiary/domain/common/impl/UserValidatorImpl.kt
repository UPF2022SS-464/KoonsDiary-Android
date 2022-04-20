package com.upf464.koonsdiary.domain.common.impl

import com.upf464.koonsdiary.domain.common.UserValidator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserValidatorImpl @Inject constructor() : UserValidator {

    override fun isEmailValid(email: String): Boolean {
        return email.matches("""^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[.][a-zA-Z]{2,3}$""".toRegex())
    }

    override fun isPasswordValid(password: String): Boolean {
        // 숫자, 대소문자를 포함
        // 8자 이상
        // 쿼티 키보드에서 입력할 수 있는 특수문자 포함
        return password.matches("""^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z~!@#$%^&*()\-_=+\[\]{};:'",.<>/?]{8,}$""".toRegex())
    }

    override fun isNicknameValid(nickname: String): Boolean {
        // 2자 이상, 12자 이하
        return nickname.matches("""^.{2,12}$""".toRegex())
    }

    override fun isUsernameValid(username: String): Boolean {
        // 대소문자, 숫자, "-", "_", "." 사용 가능
        // 5자 이상, 64자 이하
        return username.matches("""^[a-zA-Z][a-zA-Z0-9-_.]{4,63}$""".toRegex())
    }
}
