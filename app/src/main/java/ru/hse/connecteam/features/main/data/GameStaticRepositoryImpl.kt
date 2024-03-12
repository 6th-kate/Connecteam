package ru.hse.connecteam.features.main.data

import ru.hse.connecteam.features.main.domain.GameStaticRepository
import ru.hse.connecteam.shared.models.game.GameStatus
import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.shared.models.game.SimplePlayer
import ru.hse.connecteam.shared.utils.GAMES_CHUNK_LIMIT
import ru.hse.connecteam.shared.utils.PLAYERS_CHUNK_LIMIT
import java.util.Date

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
                games.add(SimpleGame("Participated in Game $i", status = GameStatus.entries[i % 3], Date()))
            }
            games
        } else
            null
    }
}