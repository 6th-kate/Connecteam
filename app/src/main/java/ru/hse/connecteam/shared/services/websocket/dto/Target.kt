package ru.hse.connecteam.shared.services.websocket.dto

import kotlinx.serialization.Serializable
import ru.hse.connecteam.shared.services.websocket.dto.target.GameStatus
import ru.hse.connecteam.shared.services.websocket.dto.target.Round
import ru.hse.connecteam.shared.services.websocket.dto.target.Topic

@Serializable
data class Target(
    val id: Int,
    val name: String? = null,
    val max_size: Int? = null,
    val status: GameStatus? = null,
    val creator_id: Int? = null,
    val users: List<User>? = null,
    val topics: List<Topic>? = null,
    val round: Round? = null,
)
