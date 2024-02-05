package ru.hse.connecteam.features.profile.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DefaultRed
import ru.hse.connecteam.ui.theme.DefaultRedLabel17

@Composable
fun ExitButton(onClick: () -> Unit = { }) {
    OutlinedSettingsBaseButton(text = "Выйти",
        textStyle = DefaultRedLabel17,
        onClick = onClick,
        borderBrush = Brush.linearGradient(colors = listOf(DefaultRed, DefaultRed)),
        trailingIcon = {
        Icon(
            imageVector = Icons.Filled.ExitToApp,
            tint = DefaultRed,
            contentDescription = "Выйти"
        )
    })
}


@Preview
@Composable
fun ExitButtonPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            ExitButton()
        }
    }
}