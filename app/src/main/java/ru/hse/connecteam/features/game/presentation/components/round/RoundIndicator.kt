package ru.hse.connecteam.features.game.presentation.components.round

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DefaultWhite
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

/**
 * TODO(rewrite as in figma)
 */
@Composable
fun RoundIndicator(roundNumber: Int, maxRoundCount: Int) {
    LinearProgressIndicator(
        progress = { roundNumber.toFloat() / maxRoundCount },
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        color = DefaultWhite,
        trackColor = WhiteSemiTransparent25
    )
}

@Preview
@Composable
fun RoundIndicatorPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column { RoundIndicator(roundNumber = 3, maxRoundCount = 7) }
        }
    }
}