package ru.hse.connecteam.features.profile.presentation.screens.access

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.hse.connecteam.features.tariffs.presentation.components.PayWall
import ru.hse.connecteam.ui.components.bars.TransparentAppBar
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun AccessScreen(
    viewModel: MyTariffViewModel = hiltViewModel(),
    navController: NavController,
) {
    Scaffold(
        topBar = { TransparentAppBar(title = "Доступ", navController = navController) }
    ) { innerPadding ->
        if (!viewModel.hasTariff || viewModel.tariffInfo == null) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                PayWall(navController = navController, message = "У вас ещё нет тарифа")
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
        /*else if (!viewModel.confirmed) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 10.dp, bottom = 15.dp)
            ) {
                PendingTariff(navController, tariffInfo = viewModel.tariffInfo)
            }
        } */
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 10.dp, bottom = 15.dp)
            ) {
                MyTariff(navController = navController, viewModel)
            }
        }
    }
}

@Preview
@Composable
fun AccessScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            AccessScreen(navController = rememberNavController())
        }
    }
}
