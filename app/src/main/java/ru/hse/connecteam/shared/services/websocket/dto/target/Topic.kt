package ru.hse.connecteam.shared.services.websocket.dto.target

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id: Int,
    val used: Boolean? = null,
    val title: String? = null,
    val questions: List<String>? = null,
)
