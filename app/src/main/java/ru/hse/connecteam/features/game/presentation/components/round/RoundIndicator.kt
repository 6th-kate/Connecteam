package ru.hse.connecteam.features.game.presentation.components.round

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

/**
 * TODO(rewrite as in figma)
 */
@Composable
fun RoundIndicator(roundNumber: Int, maxRoundCount: Int) {
    Box(
        modifier = Modifier
            .padding(15.dp)
            .height(4.dp)
            .fillMaxWidth()
            .background(WhiteSemiTransparent25)
    ) {
        Box(
            modifier = Modifier
                .size(
                    height = 4.dp,
                    width = ((LocalConfiguration.current.screenWidthDp.dp - 30.dp) *
                            (roundNumber.toFloat() / maxRoundCount))
                )
                .background(BaseGradientBrush)
        )
    }
}

@Preview
@Composable
fun RoundIndicatorPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                RoundIndicator(roundNumber = 3, maxRoundCount = 7)
                RoundIndicator(roundNumber = 1, maxRoundCount = 7)
                RoundIndicator(roundNumber = 1, maxRoundCount = 1)
                RoundIndicator(roundNumber = 7, maxRoundCount = 7)
            }
        }
    }
}