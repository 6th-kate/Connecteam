package ru.hse.connecteam.features.game.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun EvalStars(starCount: Int, onStarTap: (Int) -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        (1..5).forEach { i ->
            IconButton(onClick = { onStarTap(i) }) {
                GradientIcon(
                    image = if (starCount < i) Icons.Filled.StarBorder else Icons.Filled.Star,
                    description = "Оценка $i",
                    modifier = Modifier.size(height = 50.dp, width = 50.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun EvalStarsPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                EvalStars(starCount = 1)
                EvalStars(starCount = 3)
                EvalStars(starCount = 5)
            }
        }
    }
}