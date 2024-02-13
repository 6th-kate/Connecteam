package ru.hse.connecteam.features.auth.presentation.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.shared.utils.CustomCallback
import ru.hse.connecteam.shared.utils.EMAIL_REGEX
import ru.hse.connecteam.shared.utils.NAME_REGEX
import ru.hse.connecteam.shared.utils.PASSWORD_REGEX
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    var alertText by mutableStateOf("Ошибка")
        private set

    var shouldShowAlert by mutableStateOf(false)
        private set

    var name by mutableStateOf("Test")
        private set

    var surname by mutableStateOf("Test")
        private set

    var username by mutableStateOf("shestakova-er@mail.ru")
        private set

    var usernameError by mutableStateOf(false)
        private set

    var password by mutableStateOf("TestPass1")
        private set

    var passwordRepeat by mutableStateOf("TestPass1")
        private set

    var passwordError by mutableStateOf(false)
        private set

    var passwordRepeatEnabled by mutableStateOf(false)
        private set

    var passwordRepeatError by mutableStateOf(false)
        private set

    var singUpButtonText by mutableStateOf("Зарегистрироваться")
        private set

    var signUpButtonEnabled by mutableStateOf(true)
        private set

    var moveToVerification by mutableStateOf(false)
        private set

    fun updateName(input: String) {
        name = input.replace(NAME_REGEX, "")
    }

    fun updateSurname(input: String) {
        surname = input.replace(NAME_REGEX, "")
    }

    fun updatePassword(input: String) {
        password = input
        if (validatePassword(input)) {
            passwordError = false
            passwordRepeatEnabled = true
        } else {
            passwordError = true
            passwordRepeatEnabled = false
            passwordRepeat = ""
        }
    }

    fun updatePasswordRepeat(input: String) {
        passwordRepeat = input
        passwordRepeatError = !validatePasswordRepeat(password, passwordRepeat)
    }

    private fun validatePasswordRepeat(password: String, passwordRepeat: String): Boolean {
        return passwordRepeat == password
    }

    fun updateUsername(input: String) {
        username = input
        usernameError = !validateUsernameInput(input)
    }

    private var id: String = ""

    fun getVerificationParameters(): String {
        return "$username/$password/$id"
    }

    fun signUp() {
        if (signUpButtonEnabled && validateForm()) {
            signUpButtonEnabled = false
            singUpButtonText = "Проверяем..."
            repository.signUpEmail(
                email = username,
                password = password,
                name = name,
                surname = surname,
                customCallback = object : CustomCallback<String> {
                    override fun onSuccess(value: String?) {
                        if (value != null) {
                            id = value
                            singUpButtonText = "Зарегистрироваться"
                            signUpButtonEnabled = true
                            moveToVerification = true
                        } else {
                            singUpButtonText = "Зарегистрироваться"
                            signUpButtonEnabled = true
                            shouldShowAlert = true
                        }
                    }

                    override fun onFailure() {
                        singUpButtonText = "Зарегистрироваться"
                        signUpButtonEnabled = true
                        shouldShowAlert = true
                    }
                }
            )
        }
    }

    private fun validateForm(): Boolean {
        return validatePassword(password) && validatePasswordRepeat(
            password,
            passwordRepeat
        ) && (validateUsernameInput(username))
    }

    private fun validateUsernameInput(input: String): Boolean {
        return input.matches(EMAIL_REGEX)
    }

    private fun validatePassword(input: String): Boolean {
        return input.matches(PASSWORD_REGEX)
    }

    fun stopAlert() {
        shouldShowAlert = false
    }
}