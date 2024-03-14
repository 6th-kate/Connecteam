package ru.hse.connecteam.features.game.presentation.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import ru.hse.connecteam.features.game.domain.state.Loading
import ru.hse.connecteam.features.game.presentation.components.dropdown.PlayersDropDown
import ru.hse.connecteam.features.game.presentation.components.round.RoundIndicator
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme


@Composable
fun Loading(state: Loading) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = state.message,
            style = BigWhiteLabel,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
    }
}


@Preview
@Composable
fun LoadingPreview() {
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
                Loading(state = Loading("hredlhgisehrdiughurdhgyuoghdbtrgyutbrdgvyuldbtxchgjvbldhjlbxgvhcdb"))
            }
        }
    }
}