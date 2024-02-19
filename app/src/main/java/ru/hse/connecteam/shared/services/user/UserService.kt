package ru.hse.connecteam.shared.services.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import ru.hse.connecteam.shared.models.UserModel
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.api.UserData
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import javax.inject.Inject

class UserService @Inject constructor(
    private val authenticationService: AuthenticationService,
) {
    val user = MutableStateFlow<UserModel?>(null)
    suspend fun forceUpdateUserFlow() {
        withContext(Dispatchers.IO) {
            try {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.currentUser("Bearer $token")
                if (response == null || !response.isSuccessful || response.body() == null) {
                    if (response != null && response.code() == 401) {
                        authenticationService.onLogout()
                        user.value = null
                    } else {
                        user.value = null
                    }
                } else {
                    user.value = convert(response.body())
                }
            } catch (e: IllegalArgumentException) {
                authenticationService.onLogout()
                user.value = (null)
            }
        }
    }

    private fun convert(userData: UserData?): UserModel? {
        if (userData == null) {
            return null
        }
        return UserModel(
            userData.first_name,
            userData.second_name,
            null,
            userData.email,
            userData.description,
            null,
            userData.company_name,
            userData.company_url,
            userData.company_info
        )
    }
}