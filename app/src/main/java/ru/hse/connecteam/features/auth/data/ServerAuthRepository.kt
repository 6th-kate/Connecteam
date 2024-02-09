package ru.hse.connecteam.features.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.api.CodeVerification
import ru.hse.connecteam.shared.services.api.Email
import ru.hse.connecteam.shared.services.api.UserAuth
import ru.hse.connecteam.shared.services.api.UserSignIn
import ru.hse.connecteam.shared.services.api.UserSignUp
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import ru.hse.connecteam.shared.utils.CustomCallback
import ru.hse.connecteam.shared.utils.CustomVoidCallback
import javax.inject.Inject

class ServerAuthRepository @Inject constructor(
    private val authenticationService: AuthenticationService
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
}