package ru.hse.connecteam.shared.services.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import ru.hse.connecteam.shared.utils.tokenHeaderName

interface ApiService {
    @POST("auth/sign-up")
    suspend fun signUp(
        @Body user: UserSignUp
    ): Response<ID>?

    @POST("auth/verify-email")
    suspend fun getVerificationEmail(
        @Body email: Email
    ): Response<ID>?

    @POST("auth/verify-user")
    suspend fun verifyUser(
        @Body codeVerification: CodeVerification,
    ): Response<Void>?

    @POST("auth/sign-in/email")
    suspend fun signIn(
        @Body user: UserSignIn
    ): Response<UserAuth>?

    @GET("users/me")
    suspend fun currentUser(
        @Header(tokenHeaderName) token: String
    ): Response<UserData>?

    @PATCH("users/change-access")
    suspend fun changeAccess(
        @Header(tokenHeaderName) token: String,
        @Body change: AccessChange
    ): Response<Void>?

    @PATCH("users/change-password")
    suspend fun changePassword(
        @Header(tokenHeaderName) token: String,
        @Body change: PasswordChange
    ): Response<Void>?

    @POST("users/verify-email")
    suspend fun verifyEmailChange(
        @Header(tokenHeaderName) token: String,
        @Body change: NewEmailVerification
    ): Response<Void>?

    @PATCH("users/change-email")
    suspend fun changeEmail(
        @Header(tokenHeaderName) token: String,
        @Body change: EmailChange
    ): Response<Void>?

    @PATCH("users/info")
    suspend fun editPersonalData(
        @Header(tokenHeaderName) token: String,
        @Body userData: UserData
    ): Response<Void>?

    @PATCH("users/company")
    suspend fun editCompanyData(
        @Header(tokenHeaderName) token: String,
        @Body companyData: CompanyData
    ): Response<Void>?

    @GET("plans/current")
    suspend fun getUserTariff(
        @Header(tokenHeaderName) token: String
    ): Response<TariffData>?

    @POST("plans/purchase")
    suspend fun purchasePlan(
        @Header(tokenHeaderName) token: String,
        @Body tariffRequest: TariffRequest
    ): Response<TariffConfirm>?
}