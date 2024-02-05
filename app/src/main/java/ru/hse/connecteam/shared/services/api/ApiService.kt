package ru.hse.connecteam.shared.services.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import ru.hse.connecteam.shared.utils.tokenHeaderName

interface ApiService {
    @POST("auth/sign-up")
    fun signUp(
        @Body user: UserSignUp
    ): Call<ResponseBody>?

    @POST("auth/verify-email")
    fun getVerificationEmail(
        @Body email: Email
    ): Call<ResponseBody>?

    @POST("auth/verify-user")
    fun verifyUser(
        @Body id: ID,
    ): Call<Void>

    @POST("auth/sign-in/email")
    fun signIn(
        @Body user: UserSignIn
    ): Call<ResponseBody>?

    @GET("users/me")
    fun currentUser(
        @Header(tokenHeaderName) token: String
    ): Call<ResponseBody>?

    @PATCH("users/change-password")
    fun changePassword(
        @Header(tokenHeaderName) token: String,
        @Body change: PasswordChange
    ): Call<ResponseBody>?

    @POST("users/verify-email")
    fun verifyEmailChange(
        @Header(tokenHeaderName) token: String,
        @Body change: NewEmailVerification
    ): Call<ResponseBody>?

    @PATCH("users/change-email")
    fun changeEmail(
        @Header(tokenHeaderName) token: String,
        @Body change: EmailChange
    ): Call<ResponseBody>?

    @PATCH("users/users/info")
    fun editPersonalData(
        @Header(tokenHeaderName) token: String,
        @Body userData: UserData
    ): Call<ResponseBody>?

    @PATCH("users/company")
    fun editCompanyData(
        @Header(tokenHeaderName) token: String,
        @Body companyData: CompanyData
    ): Call<ResponseBody>?

    @GET("users/plan")
    fun getUserTariff(
        @Header(tokenHeaderName) token: String
    ): Call<ResponseBody>?
}