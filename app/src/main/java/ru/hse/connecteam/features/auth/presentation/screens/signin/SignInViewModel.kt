package ru.hse.connecteam.features.auth.presentation.screens.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.shared.utils.CustomCallback
import ru.hse.connecteam.shared.utils.CustomVoidCallback
import ru.hse.connecteam.shared.utils.EMAIL_REGEX
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
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
        if (signInButtonEnabled && validateUsernameInput(username)) {
            singInButtonText = "Проверяем..."
            signInButtonEnabled = false
            repository.signInEmail(
                email = username,
                password = password,
                customCallback = object : CustomVoidCallback {
                    override fun onSuccess() {
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