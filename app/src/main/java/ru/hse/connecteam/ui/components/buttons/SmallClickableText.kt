package ru.hse.connecteam.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.connecteam.ui.theme.ClickableTextGrayLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun SmallClickableText(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    ClickableText(
        modifier = modifier,
        text = AnnotatedString(text),
        style = ClickableTextGrayLabel,
        onClick = { onClick() },
    )
}

@Preview
@Composable
fun SmallClickableTextPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            SmallClickableText(text = "click")
        }
    }
}