package ru.hse.connecteam.features.profile.presentation.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.shared.models.response.StatusInfo
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val repository: ProfileDataRepository
) : ViewModel() {
    var email: String = "Загружаем..."

    init {
        viewModelScope.launch {
            repository.getUserFlow().collectLatest { user ->
                email = user?.email ?: "Ошибка"
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