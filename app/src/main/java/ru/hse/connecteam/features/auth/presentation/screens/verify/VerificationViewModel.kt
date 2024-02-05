package ru.hse.connecteam.features.auth.presentation.screens.verify

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.hse.connecteam.features.auth.data.ServerAuthRepository
import ru.hse.connecteam.shared.utils.CustomCallback
import ru.hse.connecteam.shared.utils.NEW_CODE_WAITING_TIME
import ru.hse.connecteam.shared.utils.RESEND_CODE_MAX_TIMES

class VerificationViewModel(
    private val repository: ServerAuthRepository,
    private val email: String,
    private val password: String,
    private val id: String,
) : ViewModel() {
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

    var shouldMoveToMain by mutableStateOf(false)
        private set

    var shouldReturn by mutableStateOf(false)
        private set

    var alertText by mutableStateOf("")
        private set

    private var hiddenCode: String = ""

    private var firstCodeSent = false

    init {
        Log.i("info","init called")
        if (!firstCodeSent){
            repository.getVerificationEmail(
                email = email,
                customCallback = object : CustomCallback<String> {
                    override fun onSuccess(value: String?) {
                        if (value != null) {
                            Log.i("info","on success called")
                            hiddenCode = value
                            resendTimerIsTicking = true
                            firstCodeSent = true
                        } else {
                            showErrorAndPop()
                        }
                    }

                    override fun onFailure() {
                        showErrorAndPop()
                    }
                }
            )
        }
    }

    private fun showErrorAndPop(text: String = "Ошибка отправки кода") {
        alertText = text
        shouldShowAlert = true
        shouldReturn = true
    }

    fun updateCode(input: String, isFilled: Boolean): Boolean {
        code = input
        return isFilled && !validateCode(code)
    }

    fun resendCode() {
        if (!resendTimerIsTicking) {
            if (sendCodeTimes < RESEND_CODE_MAX_TIMES) {
                repository.getVerificationEmail(
                    email = email,
                    customCallback = object : CustomCallback<String> {
                        override fun onSuccess(value: String?) {
                            if (value != null) {
                                hiddenCode = value
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
                                showErrorAndPop()
                            }
                        }

                        override fun onFailure() {
                            showErrorAndPop()
                        }
                    }
                )
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

    private fun validateCode(code: String): Boolean {
        if (code == hiddenCode) {
            repository.verifyUser(
                id = id,
                customCallback = object : CustomCallback<Boolean> {
                    override fun onSuccess(value: Boolean?) {
                        repository.signInEmail(
                            email = email,
                            password = password,
                            customCallback = object : CustomCallback<String> {
                                override fun onSuccess(value: String?) {
                                    shouldMoveToMain = true
                                }

                                override fun onFailure() {
                                    showErrorAndPop("Ошибка")
                                }
                            }
                        )
                    }

                    override fun onFailure() {
                        showErrorAndPop("Ошибка")
                    }
                }
            )
        }
        return code == hiddenCode
    }
}