package ru.hse.connecteam.features.game.domain.state

import ru.hse.connecteam.features.game.domain.SelectableTopicDomainModel

class RoundTopics(
    val topics: List<SelectableTopicDomainModel>,
) : GameState()
