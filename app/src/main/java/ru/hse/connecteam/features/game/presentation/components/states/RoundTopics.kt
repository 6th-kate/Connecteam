package ru.hse.connecteam.features.game.presentation.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.SelectableTopicDomainModel
import ru.hse.connecteam.features.game.domain.TopicDomainModel
import ru.hse.connecteam.features.game.domain.state.RoundTopics
import ru.hse.connecteam.features.game.presentation.components.dropdown.PlayersDropDown
import ru.hse.connecteam.features.game.presentation.components.round.RoundIndicator
import ru.hse.connecteam.features.game.presentation.components.topics.TopicWrap
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme


@Composable
fun RoundTopics(
    state: RoundTopics,
    onTopicTap: (SelectableTopicDomainModel) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Выберите тему для раунда", style = BigWhiteLabel)
        Spacer(modifier = Modifier.height(30.dp))
        TopicWrap(items = state.topics, onTopicTap = { onTopicTap(it) })
    }
}

@Preview
@Composable
fun RoundTopicsPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val items = listOf(
                SelectableTopicDomainModel(TopicDomainModel("1", "topic 1")),
                SelectableTopicDomainModel(
                    TopicDomainModel("1", "topic 1vxfjk;dngjk;fnlj,."),
                ),
                SelectableTopicDomainModel(
                    TopicDomainModel("1", "topic 1nfdlk;nmk;"),
                ),
                SelectableTopicDomainModel(
                    TopicDomainModel("1", "topic 1"),
                    enabled = false
                ),
                SelectableTopicDomainModel(
                    TopicDomainModel("1", "topic 1fbdm;klg"),
                    enabled = false,
                )
            )
            Column {
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
                        PlayerDomainModel(
                            "1",
                            true,
                            "Player",
                            "Playerson3",
                            isMe = true,
                            isAnswering = false,
                            isGameOwner = true
                        ),
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
                RoundTopics(state = RoundTopics(items))
            }
        }
    }
}