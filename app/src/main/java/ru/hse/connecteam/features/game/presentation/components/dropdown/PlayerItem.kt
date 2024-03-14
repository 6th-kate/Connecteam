package ru.hse.connecteam.features.game.presentation.components.dropdown

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.features.game.domain.PlayerDomainModel
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.MediumWhiteLabel
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

@Composable
fun PlayerItem(
    asOwner: Boolean,
    player: PlayerDomainModel,
    onDelete: (PlayerDomainModel) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.size(
                height = 50.dp,
                width = (LocalConfiguration.current.screenWidthDp * 0.8f).dp
            )
        ) {
            Box(
                modifier = Modifier
                    .size(height = 41.dp, width = 41.dp)
                    .clip(CircleShape)
                    .then(
                        if (player.isAnswering) Modifier.border(
                            width = 1.dp,
                            brush = BaseGradientBrush,
                            shape = CircleShape
                        ) else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                GradientIcon(
                    image = Icons.Outlined.Person,
                    modifier = Modifier.size(height = 30.dp, width = 30.dp)
                )
            }
            Text(
                text = "${player.name} ${player.surname}",
                style = MediumWhiteLabel,
            )
            if (player.isMe) {
                Text(
                    text = "(Вы)",
                    style = OutlinedButtonLabel,
                )
            } else if (asOwner) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Удалить игрока",
                    modifier = Modifier.clickable { onDelete(player) }
                )
            }
        }
        if (player.isGameOwner) {
            GradientIcon(image = Icons.Rounded.Star, description = "Владелец")
        } else {
            if (player.online) {
                GradientIcon(image = Icons.Rounded.Check, description = "Онлайн")
            } else {
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = "Оффлайн",
                    tint = WhiteSemiTransparent25
                )
            }
        }
    }
}

@Preview
@Composable
fun PlayerItemPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                PlayerItem(
                    asOwner = true,
                    player = PlayerDomainModel(
                        true,
                        "Player",
                        "Playerson2",
                        isMe = false,
                        isAnswering = false,
                        isGameOwner = false
                    )
                )
                PlayerItem(
                    asOwner = true,
                    player = PlayerDomainModel(
                        true,
                        "Player",
                        "Playerson3",
                        isMe = true,
                        isAnswering = false,
                        isGameOwner = true
                    )
                )
                PlayerItem(
                    asOwner = true,
                    player = PlayerDomainModel(
                        false,
                        "Player",
                        "Playerson4",
                        isMe = false,
                        isAnswering = false,
                        isGameOwner = false
                    )
                )
                PlayerItem(
                    asOwner = true,
                    player = PlayerDomainModel(
                        true,
                        "Player",
                        "Playerson5",
                        isMe = false,
                        isAnswering = true,
                        isGameOwner = false
                    )
                )
            }
        }
    }
}

