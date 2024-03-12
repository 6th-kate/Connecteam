package ru.hse.connecteam.features.main.domain

import ru.hse.connecteam.shared.models.game.SimplePlayer

data class FiltersDomainModel(
    val player: SimplePlayer? = null,
    val gameTitle: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
)