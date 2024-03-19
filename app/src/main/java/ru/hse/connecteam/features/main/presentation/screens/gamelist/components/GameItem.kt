package ru.hse.connecteam.features.main.presentation.screens.gamelist.components

import android.content.res.Resources
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import ru.hse.connecteam.shared.models.game.GameStatus
import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DefaultRedLabel17
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel
import ru.hse.connecteam.ui.theme.SpanButtonGrayLabel
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun GameItem(
    game: SimpleGame,
    onClick: (SimpleGame) -> Unit
) {
    val currentLocale =
        ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
    val formatter = SimpleDateFormat("dd.MM.yyyy", currentLocale)
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = { onClick(game) },
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .border(
                    brush = BaseGradientBrush,
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp),
                )
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(start = 35.dp, end = 15.dp, top = 13.dp, bottom = 13.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = game.title, style = BigWhiteLabel,
                    modifier = Modifier.width(
                        (LocalConfiguration.current.screenWidthDp * 0.5f).dp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (game.date != null) formatter.format(game.date) else "",
                        style = SpanButtonWhiteLabel,
                    )
                    Text(
                        text = game.status?.desc ?: "",
                        style = when (game.status) {
                            GameStatus.NOT_STARTED -> DefaultRedLabel17
                            GameStatus.IN_PROCESS -> OutlinedButtonLabel
                            GameStatus.FINISHED -> SpanButtonGrayLabel
                            null -> DefaultRedLabel17
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GameItemPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                GameItem(
                    game = SimpleGame("", "Игра1", GameStatus.IN_PROCESS, Date()),
                    onClick = {})
                GameItem(game = SimpleGame("", "Игра2", GameStatus.FINISHED, Date()), onClick = {})
                GameItem(
                    game = SimpleGame("", "Игра3", GameStatus.NOT_STARTED, Date()),
                    onClick = {})
            }
        }
    }
}