package ru.hse.connecteam.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import ru.hse.connecteam.ui.components.inputs.PasswordTextInput
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DisabledButtonLabel
import ru.hse.connecteam.ui.theme.FilledButtonLabel
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

@Composable
fun OutlinedGradientButton(
    text: String,
    onClick: () -> Unit = { },
    enabled: Boolean = true,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = { onClick() },
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = (if (enabled) Modifier.border(
                brush = BaseGradientBrush,
                width = 1.dp,
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
            Text(text = text, style = if (enabled) OutlinedButtonLabel else DisabledButtonLabel)
        }
    }
}

@Preview
@Composable
fun OutlinedGradientButtonPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                OutlinedGradientButton("Connecteam")
                GradientFilledButton("Connecteam")
                OutlinedGradientButton("Connecteam", enabled = false)
                GradientFilledButton("Connecteam", enabled = false)
            }
        }
    }
}
