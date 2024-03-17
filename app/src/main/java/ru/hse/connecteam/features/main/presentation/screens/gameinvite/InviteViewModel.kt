package ru.hse.connecteam.features.main.presentation.screens.gameinvite

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
class InviteViewModel @Inject constructor(
    private val repository: GameStaticRepository,
    savedStateHandle: SavedStateHandle,
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

    var name by mutableStateOf("Загружаем...")
        private set
    var surname by mutableStateOf("Загружаем...")
        private set
    var shouldShowAlert by mutableStateOf(false)
        private set
    var alertText by mutableStateOf("Ошибка")
        private set
    var acceptButtonEnabled by mutableStateOf(false)
        private set
    var acceptButtonText by mutableStateOf("Присоединиться")
        private set

    init {
        viewModelScope.launch {
            repository.getUserFlow().collectLatest { user ->
                if (user != null) {
                    name = user.firstName
                    surname = user.surname
                } else {
                    name = "Ошибка"
                    surname = "Ошибка"
                }
            }
        }
    }

    fun stopAlert() {
        shouldShowAlert = false
    }

    fun accept() {
        if (invitationCode != null) {
            acceptButtonEnabled = false
            acceptButtonText = "Загружаем..."
            viewModelScope.launch {
                val response = repository.joinGame(invitationCode)
                //TODO("add response availability")
            }
        }
    }
}