package ru.hse.connecteam.features.main.presentation.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    // TODO: add repository
) : ViewModel() {
    var hasTariff by mutableStateOf(false)
        private set
    var inputDisabled by mutableStateOf(false)
        private set
    var created by mutableStateOf(false)
        private set
    var createEnabled by mutableStateOf(false)
        private set

    init {
        hasTariff = true
        if (!hasTariff) {
            inputDisabled = true
            createEnabled = false
        }
    }

    var showDatePicker by mutableStateOf(false)
        private set
    var dateValue by mutableStateOf("")
        private set
    var nameValue by mutableStateOf("")
        private set
    var gameTitleValue by mutableStateOf("")
        private set

    fun onCreate() {
        inputDisabled = true
        created = true
    }

    fun updateName(value: String) {
        nameValue = value
        createEnabled = validateForm()
    }

    fun updateDate(value: String) {
        dateValue = value
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

    private fun validateForm(): Boolean {
        return dateValue.isNotEmpty() && nameValue.isNotEmpty() && gameTitleValue.isNotEmpty()
    }
}