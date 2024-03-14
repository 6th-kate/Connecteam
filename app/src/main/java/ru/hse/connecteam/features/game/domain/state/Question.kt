package ru.hse.connecteam.features.game.domain.state

import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.QuestionDomainModel
import ru.hse.connecteam.features.game.domain.TopicDomainModel

class Question(
    val player: PlayerDomainModel,
    val topic: TopicDomainModel,
    val question: QuestionDomainModel,
) : GameState()