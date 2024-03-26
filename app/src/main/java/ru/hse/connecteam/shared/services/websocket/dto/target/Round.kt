package ru.hse.connecteam.shared.services.websocket.dto.target

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Round(
    val topic: Topic,
    @SerialName("users-questions") val usersQuestions: List<UserQuestion>? = null,
    @SerialName("users-questions-left") val usersQuestionsLeft: List<UserQuestion>,
)
