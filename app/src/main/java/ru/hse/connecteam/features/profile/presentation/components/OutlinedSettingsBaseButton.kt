package ru.hse.connecteam.features.profile.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.SmallGrayLabel
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

@Composable
fun OutlinedSettingsBaseButton(
    text: String,
    textStyle: TextStyle = SpanButtonWhiteLabel,
    topLabel: String = "",
    trailingIcon: @Composable (() -> Unit) = { GradientIcon(image = Icons.Filled.ArrowForwardIos) },
    onClick: () -> Unit = { },
    enabled: Boolean = true,
    borderBrush: Brush = BaseGradientBrush,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = { onClick() },
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 5.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = (if (enabled) Modifier.border(
                brush = borderBrush,
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
            ) else Modifier.border(
                color = WhiteSemiTransparent25,
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
            ))
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(
                    horizontal = 20.dp,
                    vertical = if (topLabel.isNotEmpty()) 10.dp else 26.dp
                ),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,
                ) {
                    if (topLabel.isNotEmpty()) {
                        Text(text = topLabel, style = SmallGrayLabel)
                    }
                    Text(text = text, style = textStyle)
                }
                trailingIcon()
            }
        }
    }
}

@Preview
@Composable
fun OutlinedSettingsBaseButtonPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            OutlinedSettingsBaseButton(text = "Connecteam", topLabel = "Connecteam")
        }
    }
}