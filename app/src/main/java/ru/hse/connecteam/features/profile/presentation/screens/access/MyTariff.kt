package ru.hse.connecteam.features.profile.presentation.screens.access

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.components.containers.TariffContainer
import ru.hse.connecteam.ui.theme.FilledButtonLabel
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel
import ru.hse.connecteam.ui.theme.SmallGrayLabel

@Composable
fun MyTariff(navController: NavController, viewModel: MyTariffViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Ваш тариф:",
                style = FilledButtonLabel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                textAlign = TextAlign.Start
            )
            TariffContainer(tariff = viewModel.tariffInfo!!)
            if (viewModel.hasParticipants) {
                OutlinedGradientButton(text = "Участники тарифа",
                    onClick = { TODO("nav to participants screen") })
            }
            Text(
                text = "Доступен до: ${viewModel.endDate}",
                style = SmallGrayLabel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                textAlign = TextAlign.Center
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (viewModel.isMyTariff!!) {
                OutlinedGradientButton(text = "Сменить тариф",
                    onClick = { TODO("nav to change tariff screen") })
                GradientFilledButton(text = "Продлить тариф",
                    onClick = {
                        TODO("nav to elongate tariff screen")
                    }
                )
            } else {
                Text(
                    "Вы являетесь участником этого тарифа",
                    style = OutlinedButtonLabel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedGradientButton(text = "Покинуть тариф",
                    onClick = { TODO("nav to leave tariff screen") })
            }
        }
    }
}