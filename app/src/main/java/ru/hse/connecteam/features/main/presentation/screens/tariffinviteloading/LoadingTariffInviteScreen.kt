package ru.hse.connecteam.features.main.presentation.screens.tariffinviteloading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.components.animated.LoadingAnimation
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.theme.BigWhiteLabel

@Composable
fun LoadingTariffInviteScreen(
    navController: NavController,
    inviteCode: String?,
    viewModel: LoadingTariffInviteViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.isLoading) {
        if (viewModel.isLoading) {
            viewModel.loadFromInviteCode(inviteCode)
        } else if (viewModel.error == null) {
            navController.navigate("${NavigationItem.TariffInviteAuth.route}/${viewModel.signInParams}") {
                popUpTo(0)
            }
        }
    }
    if (viewModel.error == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                LoadingAnimation()
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = viewModel.error.toString(),
                style = BigWhiteLabel,
                modifier = Modifier.padding(25.dp)
            )
            GradientFilledButton(text = "Пропустить", onClick = {
                navController.navigate(NavigationItem.CreateGame.route) {
                    popUpTo(0)
                }
            })
        }
    }
}