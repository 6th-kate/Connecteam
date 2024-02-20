package ru.hse.connecteam.features.tariffs.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.SmallClickableText
import ru.hse.connecteam.ui.components.containers.TariffContainer
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.MediumWhiteLabel

@Composable
fun PendingTariff(navController: NavController, tariffInfo: TariffInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(15.dp),
            text = "Ваша заявка находится на рассмотрении администратором",
            style = MediumWhiteLabel,
            textAlign = TextAlign.Start
        )
        TariffContainer(tariff = tariffInfo, isToggled = true)
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
        ) {
            SmallClickableText(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "Изменить заявку"
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        GradientFilledButton(
            text = "Назад",
            onClick = { navController.popBackStack() },
        )
    }
}

@Preview
@Composable
fun PendingTariffPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PendingTariff(navController = rememberNavController(), tariffInfo = TariffInfo.SIMPLE)
        }
    }
}