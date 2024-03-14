package ru.hse.connecteam.features.game.domain.state

import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.TopicDomainModel

class NextQuestion(
    val player: PlayerDomainModel,
    val topic: TopicDomainModel,
) : GameState()
