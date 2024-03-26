package ru.hse.connecteam.features.game.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.game.data.GameRepositoryImpl
import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.SelectableTopicDomainModel
import ru.hse.connecteam.features.game.domain.state.Evaluation
import ru.hse.connecteam.features.game.domain.state.GameState
import ru.hse.connecteam.features.game.domain.state.GameTopics
import ru.hse.connecteam.features.game.domain.state.Loading
import ru.hse.connecteam.features.game.domain.state.NextQuestion
import ru.hse.connecteam.features.game.domain.state.Question
import ru.hse.connecteam.features.game.domain.state.RoundEnded
import ru.hse.connecteam.features.game.domain.state.RoundTopics
import ru.hse.connecteam.shared.services.websocket.dto.BaseMessage
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: GameRepositoryImpl
) : ViewModel() {
    private val gameId = savedStateHandle.get<String>("id")

    private val _gameStateFlow = MutableStateFlow<GameState>(Loading("Загружаем игру..."))
    val gameStateFlow: StateFlow<GameState> get() = _gameStateFlow

    private fun setGameState(gameState: GameState) {
        _gameStateFlow.value = gameState
    }

    fun resetGameState() {
        _gameStateFlow.value = Loading("Загружаем игру...")
    }

    val players: List<PlayerDomainModel> = mutableStateListOf()

    // TopBars start

    var roundCount: Int? by mutableStateOf(null)
        private set

    var roundNumber: Int? by mutableStateOf(null)
        private set

    var asOwner: Boolean by mutableStateOf(true)
        private set

    var gameTitle: String by mutableStateOf("Загружаем...")
        private set

    fun deletePlayer(player: PlayerDomainModel) {
        // TODO: Implement
    }

    fun onExit() {
        repository.exit()
    }

    // TopBars end

    // Game topics selection start

    var topicsChooseEnabled: Boolean by mutableStateOf(false)
        private set

    private var chosenTopicsCount: Int by mutableIntStateOf(0)

    private var maxChosenTopicsNumber: Int by mutableIntStateOf(0)

    private fun resetGameTopics() {
        topicsChooseEnabled = false
        chosenTopicsCount = 0
    }

    fun onTopicTap(topic: SelectableTopicDomainModel) {
        if (gameStateFlow.value is GameTopics) {
            val index = (gameStateFlow.value as GameTopics).topics.indexOf(topic)
            if ((gameStateFlow.value as GameTopics).topics[index].enabled) {
                if ((gameStateFlow.value as GameTopics).topics[index].selected) {
                    chosenTopicsCount -= 1
                } else {
                    chosenTopicsCount += 1
                }

                (gameStateFlow.value as GameTopics).topics[index].selected =
                    !(gameStateFlow.value as GameTopics).topics[index].selected

                (gameStateFlow.value as GameTopics).topics.forEach {
                    if (chosenTopicsCount >= maxChosenTopicsNumber) {
                        if (!it.selected) {
                            it.selected = false
                        }
                    } else {
                        it.enabled = true
                    }
                }

                topicsChooseEnabled = chosenTopicsCount > 1
            }
        }
    }

    fun onTopicsChosen() {
        if (gameStateFlow.value is GameTopics && topicsChooseEnabled) {
            if (repository.sendTopics((gameStateFlow.value as GameTopics).topics)) {
                setGameState(Loading("Отправляем темы..."))
            } else {
                // TODO(EXCEPTIONS)
            }
        }
    }

    // Game topics selection end

    // Round topics selection start

    fun onRoundTopicTap(topic: SelectableTopicDomainModel) {
        if (gameStateFlow.value is RoundTopics) {
            repository.startRound(topic)
        }
    }

    // Round topics selection end

    // NextQuestion start

    fun onNextQuestionContinue() {
        if (gameStateFlow.value is NextQuestion) {
            repository.startQuestion()
        }
    }

    // NextQuestion end

    // Question start

    fun onFinishQuestion() {
        if (gameStateFlow.value is Question) {
            if (repository.finishQuestion()) {
                resetTimer()
            }
        }
    }

    private fun resetTimer() {
        questionTimerSeconds = 180
    }

    fun onTimerTick() {
        if (questionTimerSeconds > 0) {
            questionTimerSeconds -= 1
        }
    }

    var questionTimerSeconds: Int by mutableIntStateOf(180)
        private set

    // Question end

    // Evaluation start

    var starCount: Int by mutableIntStateOf(0)
        private set

    var evaluationContinueEnabled: Boolean by mutableStateOf(false)
        private set

    fun onStarTap(star: Int) {
        starCount = star
        evaluationContinueEnabled = starCount in 1..5
    }

    fun onEvaluationContinue() {
        if (gameStateFlow.value is Evaluation && evaluationContinueEnabled) {
            if (repository.endRate(
                    (gameStateFlow.value as Evaluation).player.id.toInt(),
                    starCount
                )
            ) {
                setGameState(Loading("Ожидаем оценки других игроков..."))
                resetEvaluation()
            } else {
                // TODO(EXCEPTIONS)
            }
        }
    }

    private fun resetEvaluation() {
        starCount = 0
        evaluationContinueEnabled = false
    }

    // Evaluation end

    // RoundEnded start

    fun onRoundEndedContinue() {
        if (gameStateFlow.value is RoundEnded) {
            //setGameState()
        }
    }

    fun onRoundEndedShowResults() {

    }

    // RoundEnded end

    // SumResults start

    fun onExtendPlayerResults(player: PlayerDomainModel) {

    }

    fun onSumResultsContinue() {

    }

    // SumResults end

    // PlayerResults start

    fun onPlayerResultsPopBack() {

    }

    // PlayerResults end

    init {
        if (gameId == null) {
            setGameState(Loading("Ошибка!\nНе удалось найти игру"))
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val game = repository.getGame(gameId)
                val initializedWS = repository.init(gameId)
                val maxTopics = repository.getMaxTopicsCount()
                if (game != null && initializedWS && maxTopics != null) {
                    asOwner = game.isMine
                    gameTitle = game.name
                    maxChosenTopicsNumber = maxTopics
                    repository.getIncomingMessagesFlow().collectLatest {
                        handleMessage(it)
                    }
                    setGameState(Loading("Ожидаем подключения..."))
                } else {
                    setGameState(Loading("Ошибка!\nНе удалось найти игру"))
                }
            }
        }
    }

    /// TODO(domain models instead of DTOs)
    private fun handleMessage(message: BaseMessage) {

    }
}