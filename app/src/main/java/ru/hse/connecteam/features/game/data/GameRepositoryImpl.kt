package ru.hse.connecteam.features.game.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.game.domain.GameDomainModel
import ru.hse.connecteam.features.game.domain.SelectableTopicDomainModel
import ru.hse.connecteam.shared.models.game.SimpleTopic
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import ru.hse.connecteam.shared.services.user.TariffService
import ru.hse.connecteam.shared.services.websocket.WebsocketService
import ru.hse.connecteam.shared.services.websocket.dto.BaseMessage
import ru.hse.connecteam.shared.services.websocket.dto.target.Topic
import ru.hse.connecteam.shared.services.websocket.message.MessageBuilder
import ru.hse.connecteam.shared.services.websocket.message.MessageParser
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GameRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val websocketService: WebsocketService,
    private val messageBuilder: MessageBuilder,
    private val messageParser: MessageParser,
    private val tariffService: TariffService
) {
    fun getIncomingMessagesFlow(): Flow<BaseMessage> {
        return websocketService.messages.filter {
            it != null && !it.first
        }.map {
            val msg = it!!.second
            if (msg.contains("\"payload\": [")) {
                messageParser.parsePayloadList(msg)
            } else {
                messageParser.parsePayloadObject(msg)
            }
        }
    }

    suspend fun getMaxTopicsCount(): Int? =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                tariffService.forceUpdateTariffFlow()
                continuation.resume(tariffService.tariff.value?.tariffInfo?.maxQuestionsNumber)
            }
        }

    suspend fun getTopics(): List<SimpleTopic?>? =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.getTopics(
                    "Bearer $token",
                )
                launch(Dispatchers.Main) {
                    val responseData = response?.body()
                    val topicsList = responseData?.data
                    if (response == null || !response.isSuccessful || responseData == null || topicsList == null) {
                        if (response != null && response.code() == 401) {
                            authenticationService.onLogout()
                        }
                        withContext(Dispatchers.Main) {
                            continuation.resume(null)
                        }
                    } else {
                        continuation.resume(topicsList.map {
                            if (it.id.toIntOrNull() != null) {
                                SimpleTopic(id = it.id.toInt(), topic = it.title)
                            } else {
                                null
                            }
                        })
                    }
                }
            }
        }

    suspend fun getGame(gameId: String): GameDomainModel? =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val responseUser = ApiClient.apiService.currentUser("Bearer $token")
                val response = ApiClient.apiService.getGameByID(
                    gameId,
                    token = "Bearer $token",
                )
                launch(Dispatchers.Main) {
                    val game = response?.body()
                    val user = responseUser?.body()
                    if (response == null || !response.isSuccessful || game == null ||
                        responseUser == null || !responseUser.isSuccessful || user == null
                    ) {
                        if (response != null && response.code() == 401) {
                            authenticationService.onLogout()
                        }
                        withContext(Dispatchers.Main) {
                            continuation.resume(null)
                        }
                    } else {
                        val isMine = user.id == game.creator_id
                        continuation.resume(GameDomainModel(isMine, game.name))
                    }
                }
            }
        }

    fun exit() {
        if (websocketService.isConnected.value) {
            websocketService.disconnect()
            websocketService.shutdown()
        }
    }

    fun sendTopics(topics: List<SelectableTopicDomainModel>): Boolean {
        return websocketService.sendMessage(
            messageBuilder.buildSelectTopicMessage(
                topics.map { Topic(it.topic.id.toInt()) }
            )
        )
    }

    fun startGame(): Boolean {
        return websocketService.sendMessage(messageBuilder.buildStartGameMessage())
    }

    fun startRound(selectedTopic: SelectableTopicDomainModel): Boolean {
        return websocketService.sendMessage(messageBuilder.buildStartRoundMessage(selectedTopic.topic.id.toInt()))
    }

    fun endRate(rateId: Int, rateValue: Int): Boolean {
        return websocketService.sendMessage(messageBuilder.buildRateUserMessage(rateId, rateValue))
    }

    fun startQuestion(): Boolean {
        return websocketService.sendMessage(messageBuilder.buildStartAnswerMessage())
    }

    fun finishQuestion(): Boolean {
        return websocketService.sendMessage(messageBuilder.buildEndAnswerMessage())
    }

    suspend fun init(gameId: String): Boolean = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val token = authenticationService.getToken()
            val response = ApiClient.apiService.currentUser("Bearer $token")
            val user = response?.body()
            if (response == null || !response.isSuccessful ||
                user == null || user.id.toIntOrNull() == null ||
                gameId.toIntOrNull() == null
            ) {
                if (response?.code() == 401) {
                    authenticationService.onLogout()
                }
                continuation.resume(false)
            } else {
                websocketService.connect(token)
                messageBuilder.init(gameId.toInt(), user.id.toInt())
                if (websocketService.sendMessage(messageBuilder.buildJoinGameMessage())) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }
        }
    }
}