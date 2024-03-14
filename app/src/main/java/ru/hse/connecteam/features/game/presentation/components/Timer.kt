package ru.hse.connecteam.features.game.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.R
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.GradientMediumLabel20

@Composable
fun Timer(timerSeconds: Int) {
    Box(Modifier.padding(10.dp)) {
        Image(
            modifier = Modifier.height(140.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.vectortimer),
            contentDescription = "Таймер"
        )
        Column(
            modifier = Modifier
                .height(140.dp)
                .padding(start = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {
            Spacer(modifier = Modifier.height(67.dp))
            Text(
                text = "${
                    (timerSeconds / 60).toString().padStart(2, '0')
                }:${(timerSeconds % 60).toString().padStart(2, '0')}",
                style = GradientMediumLabel20,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview
@Composable
fun TimerPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Timer(timerSeconds = 180)
                Timer(timerSeconds = 50)
                Timer(timerSeconds = 0)
            }
        }
    }
}