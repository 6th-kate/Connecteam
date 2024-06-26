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
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.shared.models.tariffs.TariffStatus
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.components.containers.TariffContainer
import ru.hse.connecteam.ui.theme.FilledButtonLabel
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel
import ru.hse.connecteam.ui.theme.SmallGrayLabel

@Composable
fun MyTariff(
    navController: NavController,
    viewModel: MyTariffViewModel
) {
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
            if (viewModel.hasParticipants && viewModel.isMyTariff!!) {
                OutlinedGradientButton(
                    text = "Участники тарифа",
                    onClick = { navController.navigate(NavigationItem.TariffParticipants.route) })
            }
            Text(
                text = when (viewModel.tariffStatus) {
                    TariffStatus.ON_CONFIRM -> "Ожидаем одобрение тарифа администратором..."
                    TariffStatus.ACTIVE -> "Доступен до: ${viewModel.endDate}"
                    TariffStatus.EXPIRED -> "Действие Вашего тарифа закончилось"
                    null -> "Тариф недоступен"
                },
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
            if ((viewModel.tariffStatus == TariffStatus.ACTIVE) && !viewModel.isMyTariff!!) {
                Text(
                    "Вы являетесь участником этого тарифа",
                    style = OutlinedButtonLabel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedGradientButton(text = "Покинуть тариф",
                    onClick = { viewModel.onDeleteFromTariff() })
            } else {
                OutlinedGradientButton(
                    text = if (
                        viewModel.tariffStatus == TariffStatus.ON_CONFIRM ||
                        viewModel.tariffStatus == TariffStatus.ACTIVE
                    ) "Сменить тариф" else "Выбрать тариф",
                    onClick = { navController.navigate(NavigationItem.TariffList.route) })
            }
        }
    }
}
