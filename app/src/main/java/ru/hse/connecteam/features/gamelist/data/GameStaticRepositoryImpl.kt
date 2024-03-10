package ru.hse.connecteam.features.gamelist.data

import ru.hse.connecteam.features.gamelist.domain.GameStaticRepository
import ru.hse.connecteam.shared.models.game.SimplePlayer
import ru.hse.connecteam.shared.utils.PLAYERS_CHUNK_LIMIT

class GameStaticRepositoryImpl : GameStaticRepository {
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
}