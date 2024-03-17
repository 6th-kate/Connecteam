package ru.hse.connecteam.features.main.presentation.screens.tariffinvite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.main.domain.GameStaticRepository
import javax.inject.Inject

@HiltViewModel
class TariffInviteViewModel @Inject constructor(
    private val repository: GameStaticRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val inviteText: String? =
        if (savedStateHandle.get<String>("invite").isNullOrEmpty() ||
            savedStateHandle.get<String>("invite").equals("none")
        ) null
        else savedStateHandle.get<String>("invite")

    private val invitationCode: String? =
        if (savedStateHandle.get<String>("code").isNullOrEmpty() ||
            savedStateHandle.get<String>("code").equals("none")
        ) null
        else savedStateHandle.get<String>("code")

    var shouldShowAlert by mutableStateOf(false)
        private set
    var shouldMoveToMain by mutableStateOf(false)
        private set
    var alertText by mutableStateOf("Ошибка")
        private set
    var acceptButtonEnabled by mutableStateOf(false)
        private set
    var acceptButtonText by mutableStateOf("Присоединиться")
        private set

    fun stopAlert() {
        shouldShowAlert = false
    }

    fun accept() {
        if (invitationCode != null) {
            acceptButtonEnabled = false
            acceptButtonText = "Загружаем..."
            viewModelScope.launch {
                val response = repository.joinGame(invitationCode)
                if (response) {
                    alertText = "Вы успешно присоединились к тарифу!"
                    shouldShowAlert = true
                } else {
                    alertText = "Не получилось присоединиться к тарифу..."
                    shouldShowAlert = true
                }
            }
        }
    }
}