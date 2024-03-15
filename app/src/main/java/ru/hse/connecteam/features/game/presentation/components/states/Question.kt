package ru.hse.connecteam.features.game.presentation.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.QuestionDomainModel
import ru.hse.connecteam.features.game.domain.TopicDomainModel
import ru.hse.connecteam.features.game.domain.state.Question
import ru.hse.connecteam.features.game.presentation.components.Timer
import ru.hse.connecteam.features.game.presentation.components.dropdown.PlayersDropDown
import ru.hse.connecteam.features.game.presentation.components.round.RoundIndicator
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.GradientMediumLabel20
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel

@Composable
fun Question(
    state: Question,
    onContinue: () -> Unit = {},
    onTimerTick: () -> Unit = {},
    timer: Int = 180
) {
    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000)
            onTimerTick()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = state.topic.name,
                style = BigWhiteLabel,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = state.question.question,
                style = SpanButtonWhiteLabel,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Timer(timerSeconds = timer)
            Spacer(modifier = Modifier.height(30.dp))
            if (!state.player.isMe) {
                Text(
                    text = "Отвечает игрок ${state.player.name} ${state.player.surname}",
                    style = GradientMediumLabel20,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
            }
        }
        if (state.player.isMe) {
            GradientFilledButton(
                text = "Завершить ответ",
                onClick = { onContinue() }
            )
        }
    }
}


@Preview
@Composable
fun QuestionPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                val me = PlayerDomainModel(
                    true,
                    "Player",
                    "Playerson3",
                    isMe = true,
                    isAnswering = false,
                    isGameOwner = true
                )
                PlayersDropDown(
                    asOwner = true, gameTitle = "Игра 1", players = listOf(
                        PlayerDomainModel(
                            true,
                            "Player",
                            "Playerson2",
                            isMe = false,
                            isAnswering = false,
                            isGameOwner = false
                        ),
                        me,
                        PlayerDomainModel(
                            false,
                            "Player",
                            "Playerson4",
                            isMe = false,
                            isAnswering = false,
                            isGameOwner = false
                        ),
                        PlayerDomainModel(
                            true,
                            "Player",
                            "Playerson5",
                            isMe = false,
                            isAnswering = true,
                            isGameOwner = false
                        )
                    )
                )
                RoundIndicator(roundNumber = 5, maxRoundCount = 7)
                Question(
                    state = Question(
                        player = me,
                        topic = TopicDomainModel("Longlogngoggnonbfdklsmfhsdvfgrbhbrhyfbdh topic"),
                        question = QuestionDomainModel("skjdnfkjnejksnfjpesnfpnlfnjakpnefirnjflksndf?")
                    )
                )
            }
        }
    }
}