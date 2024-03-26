package ru.hse.connecteam.shared.services.websocket.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Action {
    @SerialName("join-game") JOIN_GAME,
    @SerialName("join-success") JOIN_SUCCESS,
    @SerialName("select-topic") SELECT_TOPIC,
    @SerialName("start-game") START_GAME,
    @SerialName("start-round") START_ROUND,
    @SerialName("start-stage") START_STAGE,
    @SerialName("round-end") ROUND_END,
    @SerialName("game-end") GAME_END,
    @SerialName("start-answer") START_ANSWER,
    @SerialName("end-answer") END_ANSWER,
    @SerialName("rate-user") RATE_USER,
    @SerialName("rate-end") RATE_END,
}