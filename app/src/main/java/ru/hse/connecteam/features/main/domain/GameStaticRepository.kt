package ru.hse.connecteam.features.main.domain

import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.shared.models.game.SimplePlayer

interface GameStaticRepository {
    suspend fun loadPlayersChunk(offset: Int): List<SimplePlayer>?
    suspend fun loadMyGamesChunk(offset: Int): List<SimpleGame>?
    suspend fun loadParticipatedGamesChunk(offset: Int): List<SimpleGame>?
}