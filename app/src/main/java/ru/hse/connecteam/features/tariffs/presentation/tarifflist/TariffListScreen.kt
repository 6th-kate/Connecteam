package ru.hse.connecteam.features.tariffs.presentation.tarifflist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.components.buttons.SmallClickableText
import ru.hse.connecteam.ui.components.containers.TariffContainer
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun TariffListScreen(
    viewModel: TariffListViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 0.dp, vertical = 15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .weight(weight = 1f, fill = false)
        ) {
            viewModel.tariffs.forEach { tariff ->
                TariffContainer(
                    tariff,
                    isToggled = viewModel.selectedTariff == tariff,
                    onClick = { viewModel.selectTariff(tariff) }
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        GradientFilledButton(
            text = "Выбрать",
            onClick = {
                navController.navigate(
                    "${NavigationItem.TariffPurchase.route}/${viewModel.getPathParameters()}"
                )
            })
        if (viewModel.alreadyHasTariff) {
            OutlinedGradientButton(text = "Назад", onClick = { navController.popBackStack() })
            SmallClickableText(
                text = "Отказаться от тарифа",
                onClick = { viewModel.discontinueTariff() })
        } else {
            SmallClickableText(
                text = "Продолжить без тарифа",
                onClick = { navController.popBackStack() })
        }
    }
}

@Preview
@Composable
fun TariffListScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            TariffListScreen(
                //viewModel = TariffListViewModel(),
                navController = rememberNavController()
            )
        }
    }
}
