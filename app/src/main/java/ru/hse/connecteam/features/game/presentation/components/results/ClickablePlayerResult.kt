package ru.hse.connecteam.features.game.presentation.components.results

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel

@Composable
fun ClickablePlayerResult(
    player: PlayerDomainModel,
    onClick: (PlayerDomainModel) -> Unit = {}
) {
    Box(modifier = Modifier.width((LocalConfiguration.current.screenWidthDp * 0.5f).dp)) {
        Text(
            text = "${player.name} ${player.surname}",
            style = OutlinedButtonLabel,
            modifier = Modifier.align(Alignment.Center)
                .border(
                    width = 2.dp,
                    brush = BaseGradientBrush,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal =10.dp, vertical=5.dp)
                .clickable {
                    onClick(player)
                }
        )
    }
}

@Preview
@Composable
fun ClickablePlayerResultPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                ClickablePlayerResult(
                    player = PlayerDomainModel(
                        name = "player",
                        surname = "playerson1"
                    )
                )
                ClickablePlayerResult(
                    player = PlayerDomainModel(
                        name = "player",
                        surname = "playerson1"
                    )
                )
                ClickablePlayerResult(
                    player = PlayerDomainModel(
                        name = "player",
                        surname = "playerson1"
                    )
                )
                ClickablePlayerResult(
                    player = PlayerDomainModel(
                        name = "player",
                        surname = "playerson1bhdcxjbgrelbsdhjlbdhlgbvhkdilbhgl"
                    )
                )
            }
        }
    }
}