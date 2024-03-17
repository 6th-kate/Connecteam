package ru.hse.connecteam.features.main.domain

import kotlinx.coroutines.flow.Flow
import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.shared.models.game.SimplePlayer
import ru.hse.connecteam.shared.models.tariffs.TariffModel

interface GameStaticRepository {
    suspend fun loadPlayersChunk(offset: Int): List<SimplePlayer>?
    suspend fun loadMyGamesChunk(offset: Int): List<SimpleGame>?
    suspend fun loadParticipatedGamesChunk(offset: Int): List<SimpleGame>?
    suspend fun createGame(name: String, date: String): GameCreatedDomainModel?
    suspend fun getUserFlow(): Flow<UserDomainModel?>
    suspend fun getTariffFlow(): Flow<TariffModel?>

    suspend fun verifyGameInvite(inviteCode: String): GameDomainModel?
    suspend fun verifyTariffInvite(inviteCode: String): TariffDomainModel?
    suspend fun joinGame(inviteCode: String): Boolean
    suspend fun joinTariff(inviteCode: String): Boolean
    suspend fun checkGameInvite(): String?
    suspend fun checkTariffInvite(): String?
}