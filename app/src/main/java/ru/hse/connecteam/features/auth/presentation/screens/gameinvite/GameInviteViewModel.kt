package ru.hse.connecteam.features.auth.presentation.screens.gameinvite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.shared.utils.NAME_REGEX
import javax.inject.Inject

@HiltViewModel
class GameInviteViewModel @Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val inviteText =
        if (savedStateHandle.get<String>("invite").isNullOrEmpty() ||
            savedStateHandle.get<String>("invite").equals("none")
        ) null
        else savedStateHandle.get<String>("invite")

    var name by mutableStateOf("")
        private set
    var surname by mutableStateOf("")
        private set
    var shouldShowAlert by mutableStateOf(false)
        private set
    var shouldMoveToGame by mutableStateOf(false)
        private set
    var alertText by mutableStateOf("Ошибка")
        private set
    var acceptButtonEnabled by mutableStateOf(false)
        private set
    var acceptButtonText by mutableStateOf("Присоединиться")
        private set

    fun updateName(input: String) {
        name = input.replace(NAME_REGEX, "")
        acceptButtonEnabled = validateFields()
    }

    fun updateSurname(input: String) {
        surname = input.replace(NAME_REGEX, "")
        acceptButtonEnabled = validateFields()
    }

    fun stopAlert() {
        shouldShowAlert = false
    }

    private fun validateFields(): Boolean {
        return !inviteText.isNullOrEmpty() && name.isNotEmpty() && surname.isNotEmpty()
    }

    fun accept() {
        TODO("ADD GAME JOIN CODE")
        if (acceptButtonEnabled) {
            acceptButtonEnabled = false
            acceptButtonText = "Загружаем..."
            viewModelScope.launch {
                val joined = repository.joinGame(name, surname)
                shouldMoveToGame = joined
            }
        }
    }
}