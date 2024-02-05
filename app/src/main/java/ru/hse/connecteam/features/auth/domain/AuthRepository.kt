package ru.hse.connecteam.features.auth.domain

import ru.hse.connecteam.shared.utils.CustomCallback

interface AuthRepository {
    fun signInEmail(email: String, password: String, customCallback : CustomCallback<String>)
    fun signUpEmail(email: String, password: String, name: String, surname: String, customCallback : CustomCallback<String>)
    fun getVerificationEmail(email: String, customCallback : CustomCallback<String>)
    fun verifyUser(id: String, customCallback : CustomCallback<Boolean>)
}