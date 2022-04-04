package com.upf464.koonsdiary.domain.common

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserValidatorTest {

    private lateinit var validator: UserValidator

    @Before
    fun setup() {
        validator = UserValidator()
    }

    @Test
    fun isEmailValid_correctEmail_returnTrue() {
        val email = "test@test.com"
        val isValid = validator.isEmailValid(email)

        assertTrue(isValid)
    }

    @Test
    fun isEmailValid_noAt_returnFalse() {
        val email = "testtest.com"
        val isValid = validator.isEmailValid(email)

        assertFalse(isValid)
    }

    @Test
    fun isEmailValid_noDot_returnFalse() {
        val email = "test@test"
        val isValid = validator.isEmailValid(email)

        assertFalse(isValid)
    }

    @Test
    fun isPasswordValid_correctPassword_returnTrue() {
        val password = "PASSword1234"
        val isValid = validator.isPasswordValid(password)

        assertTrue(isValid)
    }

    @Test
    fun isPasswordValid_noUpperCase_returnFalse() {
        val password = "password1234"
        val isValid = validator.isPasswordValid(password)

        assertFalse(isValid)
    }

    @Test
    fun isPasswordValid_noLowerCase_returnFalse() {
        val password = "PASSWORD1234"
        val isValid = validator.isPasswordValid(password)

        assertFalse(isValid)
    }

    @Test
    fun isPasswordValid_noNumber_returnFalse() {
        val password = "PASSWORD"
        val isValid = validator.isPasswordValid(password)

        assertFalse(isValid)
    }

    @Test
    fun isPasswordValid_lessThan8_returnFalse() {
        val password = "PSwd123"
        val isValid = validator.isPasswordValid(password)

        assertFalse(isValid)
    }

    @Test
    fun isNicknameValid_correctNickname_returnTrue() {
        val nickname = "닉네임"
        val isValid = validator.isNicknameValid(nickname)

        assertTrue(isValid)
    }

    @Test
    fun isNicknameValid_lessThan2_returnFalse() {
        val nickname = "닉"
        val isValid = validator.isNicknameValid(nickname)

        assertFalse(isValid)
    }

    @Test
    fun isNicknameValid_moreThan12_returnFalse() {
        val nickname = "엄청매우많이길고긴파란만장한닉네임"
        val isValid = validator.isNicknameValid(nickname)

        assertFalse(isValid)
    }

    @Test
    fun isUsernameValid_correctUsername_returnTrue() {
        val username = "username"
        val isValid = validator.isUsernameValid(username)

        assertTrue(isValid)
    }

    @Test
    fun isUsernameValid_containsSpecial_returnFalse() {
        val username = "username!"
        val isValid = validator.isUsernameValid(username)

        assertFalse(isValid)
    }

    @Test
    fun isUsernameValid_lessThan5_returnFalse() {
        val username = "user"
        val isValid = validator.isUsernameValid(username)

        assertFalse(isValid)
    }

    @Test
    fun isUsernameValid_moreThan64_returnFalse() {
        val username = "thisisaveryverylongusernameanditislongerthansixtyfourcharacterswithoutspace"
        val isValid = validator.isUsernameValid(username)

        assertFalse(isValid)
    }

    @Test
    fun isUsernameValid_containsSpace_returnFalse() {
        val username = "username with space"
        val isValid = validator.isUsernameValid(username)

        assertFalse(isValid)
    }
}