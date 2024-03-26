package ru.hse.connecteam.shared.services.websocket.dto.payload

import kotlinx.serialization.Serializable
import ru.hse.connecteam.shared.services.websocket.dto.User
import ru.hse.connecteam.shared.services.websocket.dto.target.Topic

typealias PayloadOfTopics = List<Topic>

@Serializable
data class Payload(
    val number: Int? = null,
    val user: User? = null,
    val question: String? = null,
    val id: Int? = null,
    val user_id: Int? = null,
    val value: Int? = null,
)
