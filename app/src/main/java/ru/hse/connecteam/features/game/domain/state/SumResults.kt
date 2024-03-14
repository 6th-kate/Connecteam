package ru.hse.connecteam.features.game.domain.state

import ru.hse.connecteam.features.game.domain.PlayerSumResultDomainModel

class SumResults(val results: List<PlayerSumResultDomainModel>) : GameState()