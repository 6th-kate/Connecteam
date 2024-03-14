package ru.hse.connecteam.features.game.domain

data class SelectableTopicDomainModel(
    val topic: TopicDomainModel,
    val selected: Boolean = false,
    val enabled: Boolean = true
)
