package ru.hse.connecteam.features.game.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.hse.connecteam.features.game.domain.state.Evaluation
import ru.hse.connecteam.features.game.domain.state.GameTopics
import ru.hse.connecteam.features.game.domain.state.Loading
import ru.hse.connecteam.features.game.domain.state.NextQuestion
import ru.hse.connecteam.features.game.domain.state.PlayerResults
import ru.hse.connecteam.features.game.domain.state.Question
import ru.hse.connecteam.features.game.domain.state.RoundEnded
import ru.hse.connecteam.features.game.domain.state.RoundTopics
import ru.hse.connecteam.features.game.domain.state.SumResults
import ru.hse.connecteam.features.game.presentation.components.dropdown.PlayersDropDown
import ru.hse.connecteam.features.game.presentation.components.round.RoundIndicator
import ru.hse.connecteam.features.game.presentation.components.states.Evaluation
import ru.hse.connecteam.features.game.presentation.components.states.GameTopics
import ru.hse.connecteam.features.game.presentation.components.states.Loading
import ru.hse.connecteam.features.game.presentation.components.states.NextQuestion
import ru.hse.connecteam.features.game.presentation.components.states.PlayerResults
import ru.hse.connecteam.features.game.presentation.components.states.Question
import ru.hse.connecteam.features.game.presentation.components.states.RoundEnded
import ru.hse.connecteam.features.game.presentation.components.states.RoundTopics
import ru.hse.connecteam.features.game.presentation.components.states.SumResults

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val gameState by viewModel.gameStateFlow.collectAsState()
    Column {
        PlayersDropDown(
            asOwner = viewModel.asOwner,
            gameTitle = viewModel.gameTitle,
            players = viewModel.players,
            onExit = { viewModel.onExit() },
            onDelete = { viewModel.deletePlayer(it) }
        )
        if (viewModel.roundNumber != null && viewModel.roundCount != null) {
            RoundIndicator(
                roundNumber = viewModel.roundNumber ?: 0,
                maxRoundCount = viewModel.roundCount ?: 0
            )
        }
        when (gameState) {
            is Loading -> Loading(
                state = gameState as Loading
            )

            is GameTopics -> GameTopics(
                state = gameState as GameTopics,
                onTopicTap = { viewModel.onTopicTap(it) },
                onTopicsChosen = { viewModel.onTopicsChosen() },
                topicChooseEnabled = viewModel.topicsChooseEnabled
            )

            is RoundTopics -> RoundTopics(
                state = gameState as RoundTopics,
                onTopicTap = { viewModel.onRoundTopicTap(it) }
            )

            is NextQuestion -> NextQuestion(
                state = gameState as NextQuestion,
                onContinueClick = { viewModel.onNextQuestionContinue() }
            )

            is Question -> Question(
                state = gameState as Question,
                onContinue = { viewModel.onFinishQuestion() },
                onTimerTick = { viewModel.onTimerTick() },
                timer = viewModel.questionTimerSeconds
            )

            is Evaluation -> Evaluation(
                state = gameState as Evaluation,
                starCount = viewModel.starCount,
                onStarTap = { viewModel.onStarTap(it) },
                continueEnabled = viewModel.evaluationContinueEnabled,
                onContinue = { viewModel.onEvaluationContinue() }
            )

            is RoundEnded -> RoundEnded(
                state = gameState as RoundEnded,
                onResultsTap = { viewModel.onRoundEndedShowResults() },
                onContinue = { viewModel.onRoundEndedContinue() },
            )

            is SumResults -> SumResults(
                state = gameState as SumResults,
                onContinue = { viewModel.onSumResultsContinue() },
                onResultClick = if (viewModel.asOwner) {
                    { viewModel.onExtendPlayerResults(it) }
                } else null
            )

            is PlayerResults -> PlayerResults(
                state = gameState as PlayerResults,
                onBack = { viewModel.onPlayerResultsPopBack() }
            )

            else -> Loading(
                state = Loading("Ошибка")
            )
        }
    }
}