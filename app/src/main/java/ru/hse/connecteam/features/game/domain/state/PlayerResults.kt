package ru.hse.connecteam.features.game.domain.state

import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.QuestionResultDomainModel

class PlayerResults(
    val player: PlayerDomainModel,
    val results: List<QuestionResultDomainModel>
) : GameState()