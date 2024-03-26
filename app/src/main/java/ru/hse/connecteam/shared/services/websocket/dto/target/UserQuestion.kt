package ru.hse.connecteam.shared.services.websocket.dto.target

import kotlinx.serialization.Serializable
import ru.hse.connecteam.shared.services.websocket.dto.User

@Serializable
data class UserQuestion(
    val number: Int,
    val user: User,
    val question: String,
    val rates: List<Rate>? = null,
)
