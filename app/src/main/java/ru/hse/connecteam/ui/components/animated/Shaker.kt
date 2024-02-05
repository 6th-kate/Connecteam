package ru.hse.connecteam.ui.components.animated

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import kotlin.math.roundToInt


@Composable
fun Shaker(trigger: Long, content: @Composable () -> Unit) {
    val shake = remember { Animatable(0f) }
    LaunchedEffect(trigger) {
        if (trigger != 0L) {
            for (i in 0..10) {
                when (i % 2) {
                    0 -> shake.animateTo(5f, spring(stiffness = 100_000f))
                    else -> shake.animateTo(-5f, spring(stiffness = 100_000f))
                }
            }
            shake.animateTo(0f)
        }
    }
    Box(
        modifier = Modifier
            .offset { IntOffset(shake.value.roundToInt(), y = 0) }
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        content()
    }
}

@Preview
@Composable
fun ShakerPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            var trigger by remember { mutableLongStateOf(0L) }
            Shaker(trigger = trigger) {
                ClickableText(
                    text = AnnotatedString("Shake me"),
                    onClick = { trigger = System.currentTimeMillis() },
                )
            }
        }
    }
}