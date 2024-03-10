package ru.hse.connecteam.features.gamelist.domain

import ru.hse.connecteam.shared.models.game.SimplePlayer

interface GameStaticRepository {
    suspend fun loadPlayersChunk(offset: Int): List<SimplePlayer>?
}