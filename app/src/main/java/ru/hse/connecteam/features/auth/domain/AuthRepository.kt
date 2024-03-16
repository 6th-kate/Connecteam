package ru.hse.connecteam.features.auth.domain

import ru.hse.connecteam.shared.utils.CustomCallback
import ru.hse.connecteam.shared.utils.CustomVoidCallback

interface AuthRepository {
    fun signInEmail(email: String, password: String, customCallback: CustomVoidCallback)
    fun signUpEmail(
        email: String,
        password: String,
        name: String,
        surname: String,
        customCallback: CustomCallback<String>
    )

    fun sendVerificationEmail(email: String, customCallback: CustomCallback<String>)
    fun verifyUser(id: String, code: String, customCallback: CustomVoidCallback)

    suspend fun verifyGameInvite(inviteCode: String): GameDomainModel?
    suspend fun verifyTariffInvite(inviteCode: String): TariffDomainModel?
    suspend fun joinGame(
        //inviteCode: String,
        name: String,
        surname: String
    ): Boolean
}