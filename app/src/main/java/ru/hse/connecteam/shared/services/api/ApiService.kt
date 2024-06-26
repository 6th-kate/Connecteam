package ru.hse.connecteam.shared.services.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("topics/")
    suspend fun getTopics(
        @Header(tokenHeaderName) token: String
    ): Response<Data<Topic>>?

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
        @Body userData: UserPersonal
    ): Response<Void>?

    @PATCH("users/company")
    suspend fun editCompanyData(
        @Header(tokenHeaderName) token: String,
        @Body companyData: CompanyData
    ): Response<Void>?

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: String,
    ): Response<UserByIdData>?

    @GET("plans/current")
    suspend fun getUserTariff(
        @Header(tokenHeaderName) token: String
    ): Response<TariffData>?

    @POST("plans/purchase")
    suspend fun purchasePlan(
        @Header(tokenHeaderName) token: String,
        @Body tariffRequest: TariffRequest
    ): Response<TariffConfirm>?

    @POST("plans/trial")
    suspend fun activateTrial(
        @Header(tokenHeaderName) token: String,
    ): Response<TrialData>?

    @GET("validate/plan/{code}")
    suspend fun validateTariffInvite(
        @Path("code") code: String,
    ): Response<ID>?

    @GET("plans/members/{code}")
    suspend fun getTariffMembers(
        @Path("code") code: String,
        @Header(tokenHeaderName) token: String,
    ): Response<Data<TariffMember>>?

    @POST("plans/join/{code}")
    suspend fun joinTariff(
        @Path("code") code: String,
        @Header(tokenHeaderName) token: String,
    ): Response<JoinTariffData>?

    @DELETE("plans/{user_id}")
    suspend fun deleteTariffMember(
        @Path("user_id") userId: String,
        @Header(tokenHeaderName) token: String,
    ): Response<Status>?

    @POST("games/")
    suspend fun createGame(
        @Header(tokenHeaderName) token: String,
        @Body createGame: CreateGame
    ): Response<GameCreated>?

    @GET("games/created/{page}")
    suspend fun getMyGames(
        @Path("page") page: String,
        @Header(tokenHeaderName) token: String,
    ): Response<Data<GameBasic>>?

    @GET("games/all/{page}")
    suspend fun getParticipatedGames(
        @Path("page") page: String,
        @Header(tokenHeaderName) token: String,
    ): Response<Data<GameBasic>>?

    @POST("games/{code}")
    suspend fun addToGame(
        @Path("code") code: String,
        @Header(tokenHeaderName) token: String,
    ): Response<Status>?

    @GET("validate/game/{code}")
    suspend fun validateGameInvite(
        @Path("code") code: String,
    ): Response<GameCreated>?

    @GET("games/{id}")
    suspend fun getGameByID(
        @Path("id") id: String,
        @Header(tokenHeaderName) token: String,
    ): Response<GameBasic>?
}