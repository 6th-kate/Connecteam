package ru.hse.connecteam.features.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.features.auth.domain.GameDomainModel
import ru.hse.connecteam.features.auth.domain.TariffDomainModel
import ru.hse.connecteam.features.auth.domain.DTOConverter
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.api.Code
import ru.hse.connecteam.shared.services.api.CodeVerification
import ru.hse.connecteam.shared.services.api.Email
import ru.hse.connecteam.shared.services.api.GameCreated
import ru.hse.connecteam.shared.services.api.ID
import ru.hse.connecteam.shared.services.api.UserByIdData
import ru.hse.connecteam.shared.services.api.UserSignIn
import ru.hse.connecteam.shared.services.api.UserSignUp
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import ru.hse.connecteam.shared.services.datastore.UserStatePreferences
import ru.hse.connecteam.shared.utils.CustomCallback
import ru.hse.connecteam.shared.utils.CustomVoidCallback
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ServerAuthRepository @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userStatePreferences: UserStatePreferences,
) : AuthRepository {
    override fun signInEmail(
        email: String,
        password: String,
        customCallback: CustomVoidCallback
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.signIn(UserSignIn(email, password))
            val auth = response?.body()
            if (response == null || !response.isSuccessful ||
                auth == null || auth.token.isEmpty()
            ) {
                launch(Dispatchers.Main) {
                    customCallback.onFailure()
                }
            } else {
                authenticationService.store(token = auth.token)
                launch(Dispatchers.Main) {
                    customCallback.onSuccess()
                }
            }
        }
    }

    override fun signUpEmail(
        email: String,
        password: String,
        name: String,
        surname: String,
        customCallback: CustomCallback<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.signUp(
                UserSignUp(
                    email,
                    "",
                    name,
                    surname,
                    password,
                )
            )
            val auth = response?.body()
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful ||
                    auth == null || auth.id.isEmpty()
                ) {
                    customCallback.onFailure()
                } else {
                    customCallback.onSuccess(auth.id)
                }
            }
        }
    }

    override fun sendVerificationEmail(
        email: String,
        customCallback: CustomCallback<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.getVerificationEmail(Email(email))
            val auth = response?.body()
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful ||
                    auth == null || auth.id.isEmpty()
                ) {
                    customCallback.onFailure()
                } else {
                    customCallback.onSuccess(auth.id)
                }
            }
        }
    }

    override fun verifyUser(
        id: String,
        code: String,
        customCallback: CustomVoidCallback
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.verifyUser(CodeVerification(id, code))
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful) {
                    customCallback.onFailure()
                } else {
                    customCallback.onSuccess()
                }
            }
        }
    }

    override suspend fun verifyGameInvite(inviteCode: String): GameDomainModel? =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val response = ApiClient.apiService.validateGameInvite(inviteCode)
                if (response == null || !response.isSuccessful ||
                    response.body() == null || response.body() !is GameCreated
                ) {
                    withContext(Dispatchers.Main) {
                        continuation.resume(null)
                    }
                } else {
                    userStatePreferences.setGameInvite(inviteCode)
                    withContext(Dispatchers.Main) {
                        continuation.resume(DTOConverter.convert(response.body()!!))
                    }
                }
            }
        }

    override suspend fun joinGame(
        //inviteCode: String,
        name: String,
        surname: String
    ): Boolean =
        suspendCoroutine { continuation ->
            continuation.resume(true)
        }

    override suspend fun verifyTariffInvite(inviteCode: String): TariffDomainModel? =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val response = ApiClient.apiService.validateTariffInvite(inviteCode)
                if (response == null || !response.isSuccessful ||
                    response.body() == null || response.body() !is ID
                ) {
                    withContext(Dispatchers.Main) {
                        continuation.resume(null)
                    }
                } else {
                    val ownerResponse = ApiClient.apiService.getUserById(response.body()!!.id)
                    if (ownerResponse == null || !ownerResponse.isSuccessful ||
                        ownerResponse.body() == null || ownerResponse.body() !is UserByIdData
                    ) {
                        withContext(Dispatchers.Main) {
                            continuation.resume(null)
                        }
                    } else {
                        userStatePreferences.setTariffInvite(inviteCode)
                        withContext(Dispatchers.Main) {
                            continuation.resume(DTOConverter.convert(ownerResponse.body()!!))
                        }
                    }
                }
            }
        }
}