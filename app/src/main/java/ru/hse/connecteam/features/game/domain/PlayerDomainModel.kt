package ru.hse.connecteam.features.game.domain

data class PlayerDomainModel(
    val id: String,
    val online: Boolean = false,
    val name: String,
    val surname: String,
    val isMe: Boolean = false,
    val isAnswering: Boolean = false,
    val isGameOwner: Boolean = false,
)