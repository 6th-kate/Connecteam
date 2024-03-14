package ru.hse.connecteam.features.game.domain

data class PlayerSumResultDomainModel(
    val playerDomainModel: PlayerDomainModel,
    val sumPoints: Int
)