package ru.hse.connecteam.features.auth.presentation.screens.verify

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
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.shared.utils.CustomCallback
import ru.hse.connecteam.shared.utils.CustomVoidCallback
import ru.hse.connecteam.shared.utils.NEW_CODE_WAITING_TIME
import ru.hse.connecteam.shared.utils.RESEND_CODE_MAX_TIMES
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val email = savedStateHandle.get<String>("username").orEmpty()
    private val password = savedStateHandle.get<String>("password").orEmpty()
    private val id = savedStateHandle.get<String>("id").orEmpty()

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

    private var firstCodeSent = false

    init {
        if (!firstCodeSent) {
            repository.sendVerificationEmail(
                email = email,
                customCallback = object : CustomCallback<String> {
                    override fun onSuccess(value: String?) {
                        if (value != null) {
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
        if (isFilled) {
            CoroutineScope(Dispatchers.Main).launch {
                validateCode(code)
            }
        }
        return false
    }

    fun resendCode() {
        if (!resendTimerIsTicking) {
            if (sendCodeTimes < RESEND_CODE_MAX_TIMES) {
                repository.sendVerificationEmail(
                    email = email,
                    customCallback = object : CustomCallback<String> {
                        override fun onSuccess(value: String?) {
                            if (value != null) {
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

    private suspend fun validateCode(code: String): Boolean = suspendCoroutine { continuation ->
        repository.verifyUser(
            id = id,
            code = code,
            customCallback = object : CustomVoidCallback {
                override fun onSuccess() {
                    repository.signInEmail(
                        email = email,
                        password = password,
                        customCallback = object : CustomVoidCallback {
                            override fun onSuccess() {
                                shouldMoveToMain = true
                                continuation.resume(true)
                            }

                            override fun onFailure() {
                                showErrorAndPop("Ошибка")
                                continuation.resume(false)
                            }
                        }
                    )
                }

                override fun onFailure() {
                    showErrorAndPop("Ошибка")
                    continuation.resume(false)
                }
            }
        )
    }
}