package ru.hse.connecteam.features.game.presentation.components.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.features.game.domain.PlayerSumResultDomainModel
import ru.hse.connecteam.features.game.domain.QuestionResultDomainModel
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.MediumWhiteLabel
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel

@Composable
fun ResultsTable(
    items: List<PlayerSumResultDomainModel>,
    onItemClick: (PlayerDomainModel) -> Unit = {},
    clickable: Boolean = false
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (clickable) {
                    ClickablePlayerResult(
                        player = item.playerDomainModel,
                        onClick = { onItemClick(it) })
                } else {
                    Box(modifier = Modifier.width((screenWidth * 0.5f).dp)) {
                        Text(
                            "${item.playerDomainModel.name} ${item.playerDomainModel.surname}",
                            style = MediumWhiteLabel
                        )
                    }
                }
                Spacer(modifier = Modifier.width((screenWidth * 0.1f).dp))
                Box(modifier = Modifier.width((screenWidth * 0.1f).dp)) {
                    Text(
                        "${item.sumPoints}",
                        style = MediumWhiteLabel
                    )
                }
            }
        }
    }
}

@Composable
fun ResultsTable(
    items: List<QuestionResultDomainModel>
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.width((screenWidth * 0.5f).dp)) {
                    Text(
                        item.questionDomainModel.question,
                        style = SpanButtonWhiteLabel
                    )
                }
                Spacer(modifier = Modifier.width((screenWidth * 0.1f).dp))
                Box(modifier = Modifier.width((screenWidth * 0.1f).dp)) {
                    Text(
                        "${item.points}",
                        style = MediumWhiteLabel
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ResultsTablePreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ResultsTable(
                    clickable = true,
                    items = listOf(
                        PlayerSumResultDomainModel(
                            PlayerDomainModel(
                                name = "player",
                                surname = "playerson1fjsngz;njgk;n;nsfj;sn k;f"
                            ), 20
                        ),
                        PlayerSumResultDomainModel(
                            PlayerDomainModel(
                                name = "player",
                                surname = "playerson1"
                            ), 20
                        ),
                        PlayerSumResultDomainModel(
                            PlayerDomainModel(
                                name = "player",
                                surname = "playerson1"
                            ), 20
                        ),
                        PlayerSumResultDomainModel(
                            PlayerDomainModel(
                                name = "player",
                                surname = "playerson1"
                            ), 20
                        )
                    )
                )
            }
        }
    }
}