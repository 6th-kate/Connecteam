package ru.hse.connecteam.features.auth.data

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.api.Email
import ru.hse.connecteam.shared.services.api.ID
import ru.hse.connecteam.shared.services.api.UserSignIn
import ru.hse.connecteam.shared.services.api.UserSignUp
import ru.hse.connecteam.shared.utils.CustomCallback

class ServerAuthRepository : AuthRepository {
    override fun signInEmail(
        email: String,
        password: String,
        customCallback: CustomCallback<String>
    ) {
        val call = ApiClient.apiService.signIn(UserSignIn(email, password))
        enqueueCall(call, "token", customCallback)
    }

    override fun signUpEmail(
        email: String,
        password: String,
        name: String,
        surname: String,
        customCallback: CustomCallback<String>
    ) {
        val call = ApiClient.apiService.signUp(
            UserSignUp(
                email,
                "",
                name,
                surname,
                password,
            )
        )
        enqueueCall(call, "id", customCallback)
    }

    override fun getVerificationEmail(
        email: String,
        customCallback: CustomCallback<String>
    ) {
        val call = ApiClient.apiService.getVerificationEmail(Email(email))
        enqueueCall(call, "confirmationCode", customCallback)
    }

    override fun verifyUser(
        id: String,
        customCallback: CustomCallback<Boolean>
    ) {
        /*val call = ApiClient.apiService.verifyUser(ID(id))
        call.enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    customCallback.onSuccess(true)
                } else {
                    customCallback.onFailure()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                customCallback.onFailure()
            }
        })*/
    }

    private fun enqueueCall(
        call: Call<ResponseBody>?,
        argumentName: String,
        customCallback: CustomCallback<String>
    ) {
        call?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    val token = (response.body()?.string()
                        ?.let { JSONObject(it).get(argumentName) }).toString()
                    customCallback.onSuccess(token)
                } else {
                    customCallback.onFailure()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                customCallback.onFailure()
            }
        })
    }
}