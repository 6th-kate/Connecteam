package ru.hse.connecteam.shared.services.websocket.dto

import kotlinx.serialization.Serializable
import ru.hse.connecteam.shared.services.websocket.dto.payload.Payload
import ru.hse.connecteam.shared.services.websocket.dto.payload.PayloadOfTopics

open class BaseMessage()

@Serializable
data class Message(
    val action: Action,
    val target: Target,
    val sender: Sender? = null,
) : BaseMessage()

@Serializable
data class MessageListPayload(
    val action: Action,
    val target: Target,
    val sender: Sender? = null,
    val payload: PayloadOfTopics? = null,
    val time: String? = null,
) : BaseMessage()

@Serializable
data class MessagePayload(
    val action: Action,
    val target: Target,
    val sender: Sender? = null,
    val payload: Payload? = null,
    val time: String? = null,
) : BaseMessage()
