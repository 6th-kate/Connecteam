package ru.hse.connecteam.features.main.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.main.domain.DTOConverter
import ru.hse.connecteam.features.main.domain.GameDomainModel
import ru.hse.connecteam.features.main.domain.GameStaticRepository
import ru.hse.connecteam.features.main.domain.TariffDomainModel
import ru.hse.connecteam.features.main.domain.UserDomainModel
import ru.hse.connecteam.shared.models.game.GameStatus
import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.shared.models.game.SimplePlayer
import ru.hse.connecteam.shared.models.response.ResponseInfo
import ru.hse.connecteam.shared.models.response.StatusInfo
import ru.hse.connecteam.shared.models.user.UserModel
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.api.Code
import ru.hse.connecteam.shared.services.api.GameCreated
import ru.hse.connecteam.shared.services.api.ID
import ru.hse.connecteam.shared.services.api.NewEmailVerification
import ru.hse.connecteam.shared.services.api.UserByIdData
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import ru.hse.connecteam.shared.services.datastore.UserStatePreferences
import ru.hse.connecteam.shared.services.user.TariffService
import ru.hse.connecteam.shared.services.user.UserService
import ru.hse.connecteam.shared.utils.GAMES_CHUNK_LIMIT
import ru.hse.connecteam.shared.utils.PLAYERS_CHUNK_LIMIT
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GameStaticRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val tariffService: TariffService,
    private val authenticationService: AuthenticationService,
    private val userStatePreferences: UserStatePreferences,
) : GameStaticRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUserFlow(): Flow<UserDomainModel?> {
        userService.forceUpdateUserFlow()
        return userService.user.flatMapLatest { value: UserModel? ->
            flow { emit(DTOConverter.convert(value)) }
        }
    }

    override suspend fun loadPlayersChunk(offset: Int): List<SimplePlayer>? {
        return if (offset < PLAYERS_CHUNK_LIMIT * 3) {
            val players = mutableListOf<SimplePlayer>()
            for (i in offset..<offset + PLAYERS_CHUNK_LIMIT) {
                players.add(SimplePlayer("player", "playerson$i"))
            }
            players
        } else
            null
    }

    override suspend fun loadMyGamesChunk(offset: Int): List<SimpleGame>? {
        return if (offset < GAMES_CHUNK_LIMIT * 3) {
            val games = mutableListOf<SimpleGame>()
            for (i in offset..<offset + GAMES_CHUNK_LIMIT) {
                games.add(SimpleGame("My Game $i", status = GameStatus.entries[i % 3], Date()))
            }
            games
        } else
            null
    }

    override suspend fun loadParticipatedGamesChunk(offset: Int): List<SimpleGame>? {
        return if (offset < GAMES_CHUNK_LIMIT * 3) {
            val games = mutableListOf<SimpleGame>()
            for (i in offset..<offset + GAMES_CHUNK_LIMIT) {
                games.add(
                    SimpleGame(
                        "Participated in Game $i",
                        status = GameStatus.entries[i % 3],
                        Date()
                    )
                )
            }
            games
        } else
            null
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
                    withContext(Dispatchers.Main) {
                        continuation.resume(DTOConverter.convert(response.body()!!))
                    }
                }
            }
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
                        withContext(Dispatchers.Main) {
                            continuation.resume(DTOConverter.convert(ownerResponse.body()!!))
                        }
                    }
                }
            }
        }

    override suspend fun joinGame(inviteCode: String): Boolean = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val token = authenticationService.getToken()
            val response = ApiClient.apiService.addToGame(
                inviteCode,
                token = "Bearer $token",
            )
            // TODO("join game")
            launch(Dispatchers.Main) {
                if (response == null || !response.isSuccessful) {
                    continuation.resume(false)
                } else {
                    continuation.resume(true)
                }
            }
        }
    }

    override suspend fun checkGameInvite(): String? = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val response = userStatePreferences.getGameInvite().last()
            launch(Dispatchers.Main) {
                if (response.isEmpty()) {
                    continuation.resume(null)
                } else {
                    continuation.resume(response)
                }
            }
        }
    }

    override suspend fun checkTariffInvite(): String? = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val response = userStatePreferences.getTariffInvite().last()
            launch(Dispatchers.Main) {
                if (response.isEmpty()) {
                    continuation.resume(null)
                } else {
                    continuation.resume(response)
                }
            }
        }
    }

    override suspend fun joinTariff(inviteCode: String): Boolean =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.joinTariff(
                    inviteCode,
                    token = "Bearer $token",
                )
                if (response == null || !response.isSuccessful || response.body() == null) {
                    if (response != null && response.code() == 401) {
                        authenticationService.onLogout()
                    }
                    withContext(Dispatchers.Main) {
                        continuation.resume(false)
                    }
                } else {
                    userStatePreferences.setTariffInvite("")
                    tariffService.forceUpdateTariffFlow()
                    withContext(Dispatchers.Main) {
                        continuation.resume(true)
                    }
                }
            }
        }
}