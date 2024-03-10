package ru.hse.connecteam.features.gamelist.domain

import ru.hse.connecteam.shared.models.game.SimplePlayer

data class FiltersDomainModel(
    val player: SimplePlayer?,
    val gameTitle: String?,
    val startDate: Long?,
    val endDate: Long?,
)