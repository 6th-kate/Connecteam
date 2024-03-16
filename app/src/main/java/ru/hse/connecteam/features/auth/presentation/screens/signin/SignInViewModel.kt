package ru.hse.connecteam.features.auth.presentation.screens.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.shared.utils.CustomVoidCallback
import ru.hse.connecteam.shared.utils.EMAIL_REGEX
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val inviteText: String? =
        if (savedStateHandle.get<String>("invite").isNullOrEmpty() ||
        savedStateHandle.get<String>("invite").equals("none")) null
        else savedStateHandle.get<String>("invite")

    var alertText by mutableStateOf("Ошибка")
        private set

    val enabledButtonText =
        if (inviteText.isNullOrEmpty()) "Войти"
        else "Присоединиться"

    var shouldShowAlert by mutableStateOf(false)
        private set

    var shouldMoveToMain by mutableStateOf(false)
        private set

    var username by mutableStateOf("")
        private set

    var usernameError by mutableStateOf(false)
        private set

    var password by mutableStateOf("")
        private set

    var singInButtonText by mutableStateOf(enabledButtonText)
        private set

    var signInButtonEnabled by mutableStateOf(true)
        private set

    fun signIn() {
        if (signInButtonEnabled && validateUsernameInput(username)) {
            singInButtonText = "Проверяем..."
            signInButtonEnabled = false
            repository.signInEmail(
                email = username,
                password = password,
                customCallback = object : CustomVoidCallback {
                    override fun onSuccess() {
                        singInButtonText = enabledButtonText
                        signInButtonEnabled = true
                        shouldMoveToMain = true
                    }

                    override fun onFailure() {
                        singInButtonText = enabledButtonText
                        signInButtonEnabled = true
                        shouldShowAlert = true
                    }
                }
            )
        }
    }

    fun updateUsername(input: String) {
        username = input
        usernameError = !validateUsernameInput(input)
    }

    fun updatePassword(input: String) {
        password = input
    }

    fun stopAlert() {
        shouldShowAlert = false
    }

    private fun validateUsernameInput(input: String): Boolean {
        return input.matches(EMAIL_REGEX)
    }
}