package ru.hse.connecteam.shared.models.game

import java.util.Date

data class SimpleGame(
    val id: String,
    val title: String,
    val status: GameStatus?,
    val date: Date?
)
