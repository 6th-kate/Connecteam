package ru.hse.connecteam.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DisabledButtonLabel
import ru.hse.connecteam.ui.theme.FilledButtonLabel
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

@Composable
fun GradientFilledButton(
    text: String,
    hasDefaultPadding: Boolean = true,
    onClick: () -> Unit = { },
    enabled: Boolean = true,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = {
            if (enabled) {
                onClick()
            }
        },

        contentPadding = if (hasDefaultPadding) PaddingValues(
            horizontal = 15.dp,
            vertical = 5.dp
        ) else PaddingValues(all = 0.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = (if (enabled) Modifier.background(
                BaseGradientBrush,
                shape = RoundedCornerShape(16.dp),
            ) else Modifier.border(
                color = WhiteSemiTransparent25,
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
            ))
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(start = 15.dp, end = 15.dp, top = 13.dp, bottom = 13.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text, style = if (enabled) FilledButtonLabel else DisabledButtonLabel)
        }
    }
}

@Preview
@Composable
fun GradientFilledButtonPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                GradientFilledButton("Connecteam")
                GradientFilledButton("Connecteam")
            }
        }
    }
}
