package ru.hse.connecteam.shared.services.websocket.message

import kotlinx.serialization.json.Json
import ru.hse.connecteam.shared.services.websocket.dto.MessageListPayload
import ru.hse.connecteam.shared.services.websocket.dto.MessagePayload
import javax.inject.Inject

class MessageParser @Inject constructor() {
    fun parsePayloadList(message: String): MessageListPayload {
        val data = Json.decodeFromString<MessageListPayload>(message)
        return data
    }

    fun parsePayloadObject(message: String): MessagePayload {
        val data = Json.decodeFromString<MessagePayload>(message)
        return data
    }
}