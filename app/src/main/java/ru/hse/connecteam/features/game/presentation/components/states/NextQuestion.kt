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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.TopicDomainModel
import ru.hse.connecteam.features.game.domain.state.NextQuestion
import ru.hse.connecteam.features.game.presentation.components.dropdown.PlayersDropDown
import ru.hse.connecteam.features.game.presentation.components.round.RoundIndicator
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.theme.BigGradientLabel23
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun NextQuestion(
    state: NextQuestion,
    onContinueClick: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = state.topic.name,
            style = BigWhiteLabel,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        if (state.player.isMe) {
            GradientFilledButton(
                text = "Вопрос для ${state.player.name} ${state.player.surname}",
                onClick = { onContinueClick?.invoke() })
        } else {
            Text(
                text = "Вопрос для ${state.player.name} ${state.player.surname}",
                style = BigGradientLabel23,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}

@Preview
@Composable
fun NextQuestionPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                val me = PlayerDomainModel(
                    "1",
                    true,
                    "Player",
                    "Playerson3",
                    isMe = false,
                    isAnswering = false,
                    isGameOwner = true
                )
                PlayersDropDown(
                    asOwner = true, gameTitle = "Игра 1", players = listOf(
                        PlayerDomainModel(
                            "1",
                            true,
                            "Player",
                            "Playerson2",
                            isMe = false,
                            isAnswering = false,
                            isGameOwner = false
                        ),
                        me,
                        PlayerDomainModel(
                            "1",
                            false,
                            "Player",
                            "Playerson4",
                            isMe = false,
                            isAnswering = false,
                            isGameOwner = false
                        ),
                        PlayerDomainModel(
                            "1",
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
                NextQuestion(
                    state = NextQuestion(
                        player = me,
                        topic = TopicDomainModel(
                            "1",
                            "Longlogngoggnonbfdklsmfhsdvfgrbhbrhyfbdh topic"
                        )
                    )
                )
            }
        }
    }
}