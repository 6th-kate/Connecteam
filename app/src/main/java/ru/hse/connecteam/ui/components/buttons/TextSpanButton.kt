package ru.hse.connecteam.ui.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import ru.hse.connecteam.ui.components.inputs.PasswordTextInput
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.SpanButtonGrayLabel
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel

@Composable
fun TextSpanButton(
    textMain: String,
    textAdditional: String,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = { onClick() },
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    brush = BaseGradientBrush,
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp),
                )
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(start = 15.dp, end = 15.dp, top = 22.dp, bottom = 22.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row {
                Text(text = textMain, style = SpanButtonWhiteLabel)
                Text(text = textAdditional, style = SpanButtonGrayLabel)
            }

        }
    }
}

@Preview
@Composable
fun TextSpanButtonPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                OutlinedGradientButton("Connecteam")
                GradientFilledButton("Connecteam")
                TextSpanButton(textMain = "Connecteam", textAdditional = "Log in")
            }
        }
    }
}
