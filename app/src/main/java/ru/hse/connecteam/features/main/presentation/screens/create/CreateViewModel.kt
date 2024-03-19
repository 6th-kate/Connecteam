package ru.hse.connecteam.features.main.presentation.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.main.domain.GameStaticRepository
import ru.hse.connecteam.shared.utils.STANDARD_BACKEND_DATE
import ru.hse.connecteam.shared.utils.getDate
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repository: GameStaticRepository
) : ViewModel() {
    var hasTariff by mutableStateOf(false)
        private set
    var inputDisabled by mutableStateOf(false)
        private set
    var created by mutableStateOf(false)
        private set
    var createEnabled by mutableStateOf(false)
        private set
    var shouldShowAlert by mutableStateOf(false)
        private set
    var alertText by mutableStateOf("Ошибка")
        private set
    var nameValue by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            repository.getTariffFlow().collectLatest { tariff ->
                hasTariff = tariff != null
                if (!hasTariff) {
                    inputDisabled = true
                    createEnabled = false
                }
            }
        }
        viewModelScope.launch {
            repository.getUserFlow().collectLatest { user ->
                nameValue = if (user == null) {
                    ""
                } else {
                    "${user.firstName} ${user.surname}"
                }
            }
        }
    }

    var showDatePicker by mutableStateOf(false)
        private set
    var dateValue by mutableStateOf("")
        private set
    private var actualDate: Long? by mutableStateOf(null)
    var gameTitleValue by mutableStateOf("")
        private set

    var subject by mutableStateOf("Приглашение в игру")
        private set
    var linkText by mutableStateOf("")
        private set

    fun onCreate() {
        if (hasTariff && validateForm()) {
            inputDisabled = true
            created = true
            viewModelScope.launch {
                val game = repository.createGame(
                    gameTitleValue,
                    date = getDate(actualDate!!, STANDARD_BACKEND_DATE)
                )
                if (game == null) {
                    alertText = "Ошибка! Не вышло создать игру."
                    shouldShowAlert = true
                } else {
                    linkText =
                        "Пользователь $nameValue приглашает Вас " +
                                "принять участие в игре $gameTitleValue, " +
                                "которая пройдет $dateValue. " +
                                "Присоединяйтесь по ссылке " +
                                "http://connecteam.ru/invite/game/${game.invitationCode}"
                    created = true
                }
            }
        }
    }

    fun updateName(value: String) {
        nameValue = value
        createEnabled = validateForm()
    }

    fun updateDate(value: String) {
        dateValue = value
        createEnabled = validateForm()
    }

    fun updateDate(value: Long?) {
        actualDate = value
        createEnabled = validateForm()
    }

    fun openDatePicker() {
        showDatePicker = true
    }

    fun hideDatePicker() {
        showDatePicker = false
    }

    fun updateGameTitle(value: String) {
        gameTitleValue = value
        createEnabled = validateForm()
    }

    fun stopAlert() {
        shouldShowAlert = false
    }

    private fun validateForm(): Boolean {
        return dateValue.isNotEmpty() && nameValue.isNotEmpty() &&
                gameTitleValue.isNotEmpty() && actualDate != null
    }
}