package ru.hse.connecteam.features.game.presentation.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DefaultRed
import ru.hse.connecteam.ui.theme.DefaultRedLabel17

@Composable
fun PlayersDropDown(
    asOwner: Boolean,
    gameTitle: String,
    players: List<PlayerDomainModel>,
    onExit: () -> Unit = {},
    onDelete: (PlayerDomainModel) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier
                .height(70.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(60.dp)
                    .clickable {
                        expanded = !expanded
                    }
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = gameTitle, style = BigWhiteLabel)
                if (!expanded) {
                    GradientIcon(image = Icons.Outlined.KeyboardArrowDown)
                }
            }
            if (!expanded) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(brush = BaseGradientBrush)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
            //.verticalScroll(scrollState)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                players.forEach { player ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(brush = BaseGradientBrush)
                    )
                    PlayerItem(asOwner, player, onDelete = { onDelete(it) })
                }
                HorizontalDivider(thickness = 2.dp, color = DefaultRed)
                Text(
                    text = if (asOwner) "Завершить игру" else "Выйти из игры",
                    style = DefaultRedLabel17,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable { onExit() }
                )
                HorizontalDivider(thickness = 2.dp, color = DefaultRed)
            }
        }
    }
}


@Preview
@Composable
fun PlayersDropDownPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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
                        PlayerDomainModel(
                            true,
                            "Player",
                            "Playerson3",
                            isMe = true,
                            isAnswering = false,
                            isGameOwner = true
                        ),
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
                Text("Undertext")
            }
        }
    }
}
