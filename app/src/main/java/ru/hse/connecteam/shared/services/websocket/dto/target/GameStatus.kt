package ru.hse.connecteam.shared.services.websocket.dto.target

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GameStatus {
    @SerialName("not_started") NOT_STARTED,
    @SerialName("in_progress") IN_PROCESS,
    @SerialName("ended") FINISHED;
}