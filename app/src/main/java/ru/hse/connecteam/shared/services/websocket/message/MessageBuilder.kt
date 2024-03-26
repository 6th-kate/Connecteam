package ru.hse.connecteam.shared.services.websocket.message

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.hse.connecteam.shared.services.websocket.dto.Action
import ru.hse.connecteam.shared.services.websocket.dto.Message
import ru.hse.connecteam.shared.services.websocket.dto.MessageListPayload
import ru.hse.connecteam.shared.services.websocket.dto.MessagePayload
import ru.hse.connecteam.shared.services.websocket.dto.Sender
import ru.hse.connecteam.shared.services.websocket.dto.Target
import ru.hse.connecteam.shared.services.websocket.dto.payload.Payload
import ru.hse.connecteam.shared.services.websocket.dto.target.Topic
import javax.inject.Inject

class MessageBuilder @Inject constructor() {
    private var gameId: Int = -1
    private var myId: Int = -1

    fun init(gameId: Int, myId: Int) {
        this.gameId = gameId
        this.myId = myId
    }

    fun buildJoinGameMessage(): String {
        val data = Message(Action.JOIN_GAME, Target(id = gameId))
        return Json.encodeToString(data)
    }

    fun buildSelectTopicMessage(topics: List<Topic>): String {
        val data =
            MessageListPayload(
                Action.SELECT_TOPIC,
                Target(id = gameId),
                Sender(id = myId),
                topics,
            )
        return Json.encodeToString(data)
    }

    fun buildStartGameMessage(): String {
        val data = Message(Action.START_GAME, Target(id = gameId))
        return Json.encodeToString(data)
    }

    fun buildStartRoundMessage(topicId: Int): String {
        val data =
            MessagePayload(Action.START_ROUND, Target(id = gameId), payload = Payload(id = topicId))
        return Json.encodeToString(data)
    }

    fun buildStartStageMessage(): String {
        val data = Message(Action.START_STAGE, Target(id = gameId), Sender(id = myId))
        return Json.encodeToString(data)
    }

    fun buildStartAnswerMessage(): String {
        val data = Message(Action.START_ANSWER, Target(id = gameId), Sender(id = myId))
        return Json.encodeToString(data)
    }

    fun buildEndAnswerMessage(): String {
        val data = Message(Action.END_ANSWER, Target(id = gameId), Sender(id = myId))
        return Json.encodeToString(data)
    }

    fun buildRateUserMessage(ratedId: Int, rateValue: Int): String {
        val data = MessagePayload(
            Action.RATE_USER,
            Target(id = gameId),
            Sender(id = myId),
            Payload(user_id = ratedId, value = rateValue)
        )
        return Json.encodeToString(data)
    }

}