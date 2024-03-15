package ru.hse.connecteam.features.game.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.SelectableTopicDomainModel
import ru.hse.connecteam.features.game.domain.state.GameState
import ru.hse.connecteam.features.game.domain.state.GameTopics
import ru.hse.connecteam.features.game.domain.state.Loading

class GameViewModel {
    private val _gameStateFlow = MutableStateFlow<GameState>(Loading("Загружаем игру..."))
    val gameStateFlow: StateFlow<GameState> get() = _gameStateFlow

    fun setGameState(gameState: GameState) {
        _gameStateFlow.value = gameState
    }

    fun resetGameState() {
        _gameStateFlow.value = Loading("Загружаем игру...")
    }

    // TopBars start

    var roundCount: Int? by mutableStateOf(null)
        private set

    var roundNumber: Int? by mutableStateOf(null)
        private set

    var asOwner: Boolean by mutableStateOf(false)
        private set

    var gameTitle: String by mutableStateOf("Загружаем")
        private set

    var players: List<PlayerDomainModel> = mutableListOf()
        private set

    fun deletePlayer(player: PlayerDomainModel) {

    }

    fun onExit() {

    }

    // TopBars end

    // Game topics selection start

    var topicsChooseEnabled: Boolean by mutableStateOf(false)
        private set

    private var chosenTopicsCount: Int by mutableStateOf(0)

    private var maxChosenTopicsNumber: Int by mutableStateOf(0)

    private fun resetGameTopics() {
        topicsChooseEnabled = false
        chosenTopicsCount = 0
        maxChosenTopicsNumber = 0
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
                            it.enabled = false
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

    }

    // Game topics selection end

    // Round topics selection start

    fun onRoundTopicTap(topic: SelectableTopicDomainModel) {

    }

    // Round topics selection end

    // NextQuestion start

    fun onNextQuestionContinue() {

    }

    // NextQuestion end

    // Question start

    fun onFinishQuestion() {
        TODO("Send question finish to server, then reset")
    }

    private fun resetTimer() {
        questionTimerSeconds = 180
    }

    fun onTimerTick() {
        if (questionTimerSeconds > 0) {
            questionTimerSeconds -= 1
        }
    }

    var questionTimerSeconds: Int by mutableStateOf(180)
        private set

    // Question end

    // Evaluation start

    var starCount: Int by mutableStateOf(0)
        private set

    var evaluationContinueEnabled: Boolean by mutableStateOf(false)
        private set

    fun onStarTap(star: Int) {
        starCount = star
        evaluationContinueEnabled = starCount in 1..5
    }

    fun onEvaluationContinue() {
        TODO("Send eval to server, then reset")
    }

    private fun resetEvaluation() {
        starCount = 0
        evaluationContinueEnabled = false
    }

    // Evaluation end

    // RoundEnded start

    fun onRoundEndedContinue() {

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

}