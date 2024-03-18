package ru.hse.connecteam.features.profile.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.profile.domain.DTOConverter
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.domain.TariffDomainModel
import ru.hse.connecteam.features.profile.domain.UserDomainModel
import ru.hse.connecteam.shared.models.response.ResponseInfo
import ru.hse.connecteam.shared.models.response.StatusInfo
import ru.hse.connecteam.shared.models.tariffs.TariffModel
import ru.hse.connecteam.shared.models.tariffs.TariffParticipant
import ru.hse.connecteam.shared.models.user.UserModel
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.api.CompanyData
import ru.hse.connecteam.shared.services.api.EmailChange
import ru.hse.connecteam.shared.services.api.NewEmailVerification
import ru.hse.connecteam.shared.services.api.PasswordChange
import ru.hse.connecteam.shared.services.api.UserPersonal
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import ru.hse.connecteam.shared.services.user.TariffService
import ru.hse.connecteam.shared.services.user.UserService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ServerProfileRepository @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
    private val tariffService: TariffService
) :
    ProfileDataRepository {
    override suspend fun getUser(): UserDomainModel? = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val token = authenticationService.getToken()
            val response = ApiClient.apiService.currentUser("Bearer $token")
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful || response.body() == null) {
                    if (response != null && response.code() == 401) {
                        authenticationService.onLogout()
                    } else {
                        continuation.resume(null)
                    }
                } else {
                    continuation.resume(DTOConverter.convert(response.body()))
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getTariffFlow(): Flow<TariffModel?> {
        tariffService.forceUpdateTariffFlow()
        return tariffService.tariff.flatMapLatest { value: TariffModel? ->
            flow { emit(value) }
        }
    }

    override suspend fun deleteParticipant(id: String): ResponseInfo =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.deleteTariffMember(id, token)
                launch(Dispatchers.Main) {
                    if (response == null || !response.isSuccessful) {
                        continuation.resume(ResponseInfo(StatusInfo.ERROR, response?.message()))
                    } else {
                        continuation.resume(ResponseInfo(StatusInfo.OK))
                    }
                }
            }
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUserFlow(): Flow<UserDomainModel?> {
        userService.forceUpdateUserFlow()
        return userService.user.flatMapLatest { value: UserModel? ->
            flow { emit(DTOConverter.convert(value)) }
        }
    }

    override suspend fun getTariff(): TariffDomainModel? = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val token = authenticationService.getToken()
            val response = ApiClient.apiService.getUserTariff(token)
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful || response.body() == null) {
                    continuation.resume(null)
                } else {
                    continuation.resume(DTOConverter.convert(response.body()))
                }
            }
        }
    }

    override suspend fun editPersonalData(
        name: String,
        surname: String,
        description: String
    ): ResponseInfo = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val token = authenticationService.getToken()
            val response = ApiClient.apiService.editPersonalData(
                "Bearer $token",
                UserPersonal(name, surname, description)
            )
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful) {
                    continuation.resume(ResponseInfo(StatusInfo.ERROR, response?.message()))
                } else {
                    userService.forceUpdateUserFlow()
                    continuation.resume(ResponseInfo(StatusInfo.OK))
                }
            }
        }
    }

    override suspend fun editCompanyData(
        name: String,
        url: String,
        description: String
    ): ResponseInfo = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val token = authenticationService.getToken()
            val response = ApiClient.apiService.editCompanyData(
                "Bearer $token",
                CompanyData(name, url, description)
            )
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful) {
                    continuation.resume(ResponseInfo(StatusInfo.ERROR, response?.message()))
                } else {
                    userService.forceUpdateUserFlow()
                    continuation.resume(ResponseInfo(StatusInfo.OK))
                }
            }
        }
    }

    override suspend fun logOut(): ResponseInfo = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            try {
                authenticationService.onLogout()
                launch(Dispatchers.Main) {
                    continuation.resume(ResponseInfo(StatusInfo.OK))
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    continuation.resume(ResponseInfo(StatusInfo.ERROR, e.message))
                }
            }
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): ResponseInfo =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.changePassword(
                    "Bearer $token",
                    PasswordChange(newPassword, oldPassword)
                )
                launch(Dispatchers.Main) {
                    if (response == null || !response.isSuccessful) {
                        continuation.resume(ResponseInfo(StatusInfo.ERROR, response?.message()))
                    } else {
                        continuation.resume(ResponseInfo(StatusInfo.OK))
                    }
                }
            }
        }

    override suspend fun getTariffParticipants(code: String): List<TariffParticipant?>? =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.getTariffMembers(
                    code,
                    "Bearer $token",
                )
                launch(Dispatchers.Main) {
                    val participantList = response?.body()
                    if (response == null || !response.isSuccessful || participantList == null) {
                        if (response != null && response.code() == 401) {
                            authenticationService.onLogout()
                        }
                        withContext(Dispatchers.Main) {
                            continuation.resume(null)
                        }
                    } else {
                        continuation.resume(participantList.map {
                            DTOConverter.convert(it)
                        })
                    }
                }
            }
        }

    override suspend fun sendChangeEmailCode(newEmail: String, password: String): ResponseInfo =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.verifyEmailChange(
                    "Bearer $token",
                    NewEmailVerification(newEmail, password)
                )
                launch(Dispatchers.Main) {
                    if (response == null || !response.isSuccessful) {
                        continuation.resume(ResponseInfo(StatusInfo.ERROR, response?.message()))
                    } else {
                        continuation.resume(ResponseInfo(StatusInfo.OK))
                    }
                }
            }
        }

    override suspend fun confirmEmailChange(newEmail: String, code: String): ResponseInfo =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.changeEmail(
                    "Bearer $token",
                    EmailChange(newEmail, code)
                )
                launch(Dispatchers.Main) {
                    if (response == null || !response.isSuccessful) {
                        if (response?.errorBody()?.string()
                                ?.contains("Wrong verification code") == true
                        ) {
                            continuation.resume(ResponseInfo(StatusInfo.UNAUTHORISED))
                        } else {
                            continuation.resume(ResponseInfo(StatusInfo.ERROR, response?.message()))
                        }
                    } else {
                        userService.forceUpdateUserFlow()
                        continuation.resume(ResponseInfo(StatusInfo.OK))
                    }
                }
            }
        }
}