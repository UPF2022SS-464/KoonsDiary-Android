package com.upf464.koonsdiary.presentation.ui.account.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.usecase.user.SignInWithAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EmailSignInViewModel @Inject constructor(
    private val signInUseCase: SignInWithAccountUseCase
) : ViewModel() {

    val usernameFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")

    private val _eventFlow = MutableSharedFlow<EmailSignInEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    fun signIn() {
        viewModelScope.launch {
            signInUseCase(
                SignInWithAccountUseCase.Request(
                    username = usernameFlow.value,
                    password = passwordFlow.value
                )
            ).onSuccess {
                _eventFlow.tryEmit(EmailSignInEvent.Success)
            }.onFailure { error ->
                when (error) {
                    SignInError.IncorrectUsernameOrPassword -> _eventFlow.tryEmit(EmailSignInEvent.Invalid)
                    else -> _eventFlow.tryEmit(EmailSignInEvent.UnknownError)
                }
            }
        }
    }
}
