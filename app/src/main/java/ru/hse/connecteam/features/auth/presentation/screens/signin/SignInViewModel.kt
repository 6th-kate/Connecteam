package ru.hse.connecteam.features.auth.presentation.screens.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import ru.hse.connecteam.features.auth.data.ServerAuthRepository
import ru.hse.connecteam.shared.utils.CustomCallback

class SignInViewModel(private val repository: ServerAuthRepository) : ViewModel() {
    var alertText by mutableStateOf("Ошибка")
        private set

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

    var singInButtonText by mutableStateOf("Войти")
        private set

    var signInButtonEnabled by mutableStateOf(true)
        private set

    fun signIn() {
        if (signInButtonEnabled) {
            validateUsernameInput(username)
            singInButtonText = "Проверяем..."
            signInButtonEnabled = false
            if (validateUsernameInput(username) == true) {
                repository.signInEmail(
                    email = username,
                    password = password,
                    customCallback = object : CustomCallback<String> {
                        override fun onSuccess(value: String?) {
                            singInButtonText = "Войти"
                            signInButtonEnabled = true
                            shouldMoveToMain = true
                        }

                        override fun onFailure() {
                            singInButtonText = "Войти"
                            signInButtonEnabled = true
                            shouldShowAlert = true
                        }
                    }
                )
            }
        }
    }

    fun updateUsername(input: String) {
        username = input
        usernameError = validateUsernameInput(input) == null
    }

    fun updatePassword(input: String) {
        password = input
    }

    fun stopAlert() {
        shouldShowAlert = false
    }

    private fun validateUsernameInput(input: String): Boolean? {
        return if (input.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))) {
            true
        } else if (input.isDigitsOnly() && input.length == 10) {
            false
        } else {
            null
        }
    }
}