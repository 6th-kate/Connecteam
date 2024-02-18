package ru.hse.connecteam.features.profile.presentation.screens.account.email.verify

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.shared.models.StatusInfo
import ru.hse.connecteam.shared.utils.NEW_CODE_WAITING_TIME
import ru.hse.connecteam.shared.utils.RESEND_CODE_MAX_TIMES
import javax.inject.Inject

@HiltViewModel
class VerifyEmailChangeViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val newEmail = savedStateHandle.get<String>("username").orEmpty()
    private val password = savedStateHandle.get<String>("password").orEmpty()

    private var sendCodeTimes by mutableIntStateOf(1)
    private var sendCodeSecondsLeft by mutableIntStateOf(NEW_CODE_WAITING_TIME)

    var code by mutableStateOf("")
        private set

    var sendCodeButtonText by mutableStateOf("Новый код через ${sendCodeSecondsLeft}с")
        private set

    var sendCodeButtonEnabled by mutableStateOf(false)
        private set

    var resendTimerIsTicking by mutableStateOf(false)
        private set

    var shouldShowAlert by mutableStateOf(false)
        private set

    var shouldReturn by mutableStateOf(false)
        private set

    var alertText by mutableStateOf("")
        private set

    private var firstCodeSent = false

    init {
        if (!firstCodeSent) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.sendChangeEmailCode(newEmail, password)
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.status == StatusInfo.OK) {
                        resendTimerIsTicking = true
                        firstCodeSent = true
                    } else {
                        showMessageAndPop()
                    }
                }
            }
        }
    }

    private fun showMessageAndPop(text: String = "Ошибка отправки кода") {
        alertText = text
        shouldShowAlert = true
        shouldReturn = true
    }

    fun updateCode(input: String, isFilled: Boolean): Boolean {
        code = input
        if (!isFilled) {
            return false
        }
        CoroutineScope(Dispatchers.Main).launch {
            validateCode(code)
        }
        return true
    }

    fun resendCode() {
        if (!resendTimerIsTicking) {
            if (sendCodeTimes < RESEND_CODE_MAX_TIMES) {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = repository.sendChangeEmailCode(newEmail, password)
                    CoroutineScope(Dispatchers.Main).launch {
                        if (response.status == StatusInfo.OK) {
                            ++sendCodeTimes
                            sendCodeButtonEnabled = false
                            if (sendCodeTimes >= RESEND_CODE_MAX_TIMES) {
                                alertText = "Код больше нельзя отправить повторно"
                                shouldShowAlert = true
                            } else {
                                resendTimerIsTicking = true
                                sendCodeSecondsLeft = NEW_CODE_WAITING_TIME
                                sendCodeButtonText = "Новый код через ${sendCodeSecondsLeft}с"
                            }
                        } else {
                            showMessageAndPop()
                        }
                    }
                }
            } else {
                alertText = "Код больше нельзя отправить повторно"
                shouldShowAlert = true
            }
        }
    }

    fun stopAlert() {
        shouldShowAlert = false
    }

    fun tickOrStop() {
        if (sendCodeSecondsLeft > 0) {
            --sendCodeSecondsLeft
            sendCodeButtonText = "Новый код через ${sendCodeSecondsLeft}с"
        } else {
            resendTimerIsTicking = false
            sendCodeButtonText = "Отправить код повторно"
            sendCodeButtonEnabled = sendCodeTimes < RESEND_CODE_MAX_TIMES
        }
    }

    private suspend fun validateCode(code: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.confirmEmailChange(newEmail, code)
            CoroutineScope(Dispatchers.Main).launch {
                if (response.status == StatusInfo.OK) {
                    sendCodeButtonEnabled = false
                    showMessageAndPop("Почта успешно изменена")
                } else if (response.status == StatusInfo.ERROR) {
                    showMessageAndPop()
                }
            }
        }
    }
}