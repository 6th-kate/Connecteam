package ru.hse.connecteam.features.profile.presentation.screens.account.email

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.shared.utils.EMAIL_REGEX
import javax.inject.Inject

@HiltViewModel
class EmailChangeViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
) : ViewModel() {
    private var initialized: Boolean = false
    var username by mutableStateOf("")
        private set

    init {
        if (!initialized) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = repository.getUser()
                CoroutineScope(Dispatchers.Main).launch {
                    username = user?.email ?: ""
                    initialized = true
                }
            }
        }
    }

    var usernameError by mutableStateOf(false)
        private set

    var password by mutableStateOf("")
        private set

    fun updatePassword(input: String) {
        password = input
        saveEnabled = !usernameError && password.isNotEmpty()
    }

    fun updateUsername(input: String) {
        username = input
        usernameError = validateUsernameInput(input)
        saveEnabled = !usernameError && password.isNotEmpty()
    }

    var saveButtonText by mutableStateOf("Сохранить")
        private set

    var saveEnabled by mutableStateOf(false)
        private set

    var moveToVerification by mutableStateOf(false)
        private set

    private fun validateUsernameInput(input: String): Boolean {
        return !input.matches(EMAIL_REGEX)
    }

    private fun validateForm(): Boolean {
        return validateUsernameInput(username)
    }

    fun onSave() {
        if (saveEnabled && validateForm()) {
            saveButtonText = "Проверяем"
            saveEnabled = false
            TODO("add password change request, then show popup and enable button")
        }
    }
}