package ru.hse.connecteam.features.game.domain

data class SelectableTopicDomainModel(
    val topic: TopicDomainModel,
    var selected: Boolean = false,
    var enabled: Boolean = true
)
