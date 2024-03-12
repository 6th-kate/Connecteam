package ru.hse.connecteam.features.main.presentation.screens.invite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.connecteam.features.auth.domain.AuthRepository
import javax.inject.Inject

@HiltViewModel
class InviteViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {
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
    var inviteText by mutableStateOf("Загружаем...")
        private set

    init {
        name = "Loaded name"
        surname = "Loaded surname"
        inviteText = "Loaded game invite"
        // take invite code from datastore
        // Delete invite from datastore
    }

    fun stopAlert() {
        shouldShowAlert = false
    }

    fun accept() {
        acceptButtonEnabled = false
        acceptButtonText = "Загружаем..."
        TODO("accept")
    }
}