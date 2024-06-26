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
import ru.hse.connecteam.features.game.domain.QuestionDomainModel
import ru.hse.connecteam.features.game.domain.QuestionResultDomainModel
import ru.hse.connecteam.features.game.domain.state.PlayerResults
import ru.hse.connecteam.features.game.presentation.components.dropdown.PlayersDropDown
import ru.hse.connecteam.features.game.presentation.components.results.ResultsTable
import ru.hse.connecteam.features.game.presentation.components.round.RoundIndicator
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun PlayerResults(
    state: PlayerResults,
    onBack: () -> Unit = {},
) {
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
                text = "Статистика ${state.player.name} ${state.player.surname}",
                style = BigWhiteLabel,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            ResultsTable(items = state.results)
        }
        GradientFilledButton(
            text = "Назад",
            onClick = { onBack() }
        )
    }
}

@Preview
@Composable
fun PlayerResultsPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val items = listOf(
                QuestionResultDomainModel(
                    QuestionDomainModel("njksd;njfk;nerjsk;ndskn;bghk;dbeghk;zehk;gbhk;edbg;kxbngjk;fn;d"),
                    20
                ),
                QuestionResultDomainModel(
                    QuestionDomainModel("njksd;njfk;nerjsk;ndskn;bghk;dbeghk;zehk;gbhk;edbg;kxbngjk;fn;d"),
                    20
                ),
                QuestionResultDomainModel(
                    QuestionDomainModel("njksd;njfk;nerjsk;ndskn;bghk;dbeghk;zehk;gbhk;edbg;kxbngjk;fn;d"),
                    20
                ),
                QuestionResultDomainModel(
                    QuestionDomainModel("njksd;njfk;nerjsk;ndskn;bghk;dbeghk;zehk;gbhk;edbg;kxbngjk;fn;d"),
                    20
                )
            )
            val me = PlayerDomainModel(
                true,
                "Player",
                "Playerson3",
                isMe = true,
                isAnswering = false,
                isGameOwner = true
            )
            Column {
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
                PlayerResults(
                    state = PlayerResults(
                        player = me,
                        items
                    )
                )
            }
        }
    }
}