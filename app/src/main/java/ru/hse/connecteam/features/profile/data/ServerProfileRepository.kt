package ru.hse.connecteam.features.profile.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.domain.DTOConverter
import ru.hse.connecteam.features.profile.domain.UserDomainModel
import ru.hse.connecteam.shared.models.TariffModel
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ServerProfileRepository @Inject constructor(
    private val authenticationService: AuthenticationService
) :
    ProfileDataRepository {
    override suspend fun getUser(
    ): UserDomainModel? = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val token = authenticationService.getToken()
            val response = ApiClient.apiService.currentUser("Bearer $token")
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful || response.body() == null) {
                    continuation.resume(null)
                } else {
                    continuation.resume(DTOConverter.convert(response.body()))
                }
            }
        }
    }

    override fun tryChangePassword(oldPassword: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getTariff(): TariffModel? = suspendCoroutine { continuation ->
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
}