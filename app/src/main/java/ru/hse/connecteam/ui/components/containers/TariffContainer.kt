package ru.hse.connecteam.ui.components.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.sp
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import ru.hse.connecteam.ui.components.utils.coloredShadow
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.LightBlueGradient
import ru.hse.connecteam.ui.theme.LightGreenGradient
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel

@Composable
fun TariffContainer(
    tariff: TariffInfo,
    isToggled: Boolean = false,
    isTrial: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = { onClick() },
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = (if (isToggled) Modifier
                .coloredShadow(
                    color = LightBlueGradient,
                    offsetX = (-4).dp,
                    offsetY = (-4).dp,
                    borderRadius = 16.dp,
                    blurRadius = 10.dp,
                    spread = 0.3f
                )
                .coloredShadow(
                    color = LightGreenGradient,
                    offsetX = 4.dp,
                    offsetY = 4.dp,
                    borderRadius = 16.dp,
                    blurRadius = 10.dp,
                    spread = 0.3f
                )
            else Modifier)
                .border(
                    brush = BaseGradientBrush,
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp),
                )
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(start = 15.dp, end = 15.dp, top = 13.dp, bottom = 13.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp)
            ) {
                Text(text = tariff.tariffName, style = BigWhiteLabel)
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = tariff.possibilities,
                    style = SpanButtonWhiteLabel.copy(lineHeight = 22.sp)
                )
            }
        }
    }
}

@Preview
@Composable
fun TariffContainerPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            TariffContainer(tariff = TariffInfo.ADVANCED, isToggled = true)
        }
    }
}