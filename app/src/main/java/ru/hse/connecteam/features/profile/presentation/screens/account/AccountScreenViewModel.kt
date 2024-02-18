package ru.hse.connecteam.features.profile.presentation.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.shared.models.StatusInfo
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val repository: ProfileDataRepository
) : ViewModel() {
    private var initialized: Boolean = false
    var email: String = "Загружаем..."

    init {
        if (!initialized) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = repository.getUser()
                CoroutineScope(Dispatchers.Main).launch {
                    email = user?.email ?: "Ошибка"
                    initialized = true
                }
            }
        }
    }
    var shouldShowAlert by mutableStateOf(false)
        private set
    var alertText by mutableStateOf("")
        private set
    fun stopAlert() {
        shouldShowAlert = false
    }

    fun logOut() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.logOut()
            CoroutineScope(Dispatchers.Main).launch {
                if (response.status != StatusInfo.OK) {
                    shouldShowAlert = true
                    alertText = "Не удалось выполнить выход"
                }
            }
        }
    }
}