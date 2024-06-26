package ru.hse.connecteam.features.profile.presentation.screens.account.password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.shared.models.response.StatusInfo
import ru.hse.connecteam.shared.utils.PASSWORD_REGEX
import javax.inject.Inject

@HiltViewModel
class PasswordChangeViewModel @Inject constructor(
    private val repository: ProfileDataRepository
) : ViewModel() {
    var oldPassword by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    var newPasswordRepeat by mutableStateOf("")
        private set

    var newPasswordError by mutableStateOf(false)
        private set

    var newPasswordRepeatEnabled by mutableStateOf(false)
        private set

    var newPasswordRepeatError by mutableStateOf(false)
        private set

    var saveButtonText by mutableStateOf("Сохранить")
        private set

    var saveEnabled by mutableStateOf(false)
        private set

    var shouldShowAlert by mutableStateOf(false)
        private set

    var alertText by mutableStateOf("")
        private set

    fun stopAlert() {
        shouldShowAlert = false
    }

    fun updateOldPassword(input: String) {
        oldPassword = input
    }

    fun updateNewPassword(input: String) {
        newPassword = input
        if (validatePassword(input)) {
            newPasswordError = false
            newPasswordRepeatEnabled = true
        } else {
            newPasswordError = true
            newPasswordRepeatEnabled = false
            newPasswordRepeat = ""
        }
        saveEnabled = newPasswordRepeatEnabled && !newPasswordRepeatError && !newPasswordError
    }

    fun updatePasswordRepeat(input: String) {
        newPasswordRepeat = input
        newPasswordRepeatError = !validatePasswordRepeat(newPassword, newPasswordRepeat)
        saveEnabled = newPasswordRepeatEnabled && !newPasswordRepeatError && !newPasswordError
    }

    private fun validatePasswordRepeat(password: String, passwordRepeat: String): Boolean {
        return passwordRepeat == password
    }

    private fun validatePassword(input: String): Boolean {
        return input.matches(PASSWORD_REGEX)
    }

    fun onSave() {
        if (saveEnabled && validateForm()) {
            saveEnabled = false
            saveButtonText = "Проверяем..."
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.changePassword(oldPassword, newPassword)
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.status == StatusInfo.OK) {
                        alertText = "Данные успешно сохранены"
                    } else {
                        saveEnabled = true
                        alertText = "Ошибка сохранения"
                    }
                    shouldShowAlert = true
                    saveButtonText = "Сохранить"
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        return validatePassword(newPassword) && validatePasswordRepeat(
            newPassword,
            newPasswordRepeat
        )
    }
}