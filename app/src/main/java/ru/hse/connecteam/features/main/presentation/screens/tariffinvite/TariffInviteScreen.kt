package ru.hse.connecteam.features.main.presentation.screens.tariffinvite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.hse.connecteam.features.auth.presentation.components.LogoLabel
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert

@Composable
fun TariffInviteScreen(
    navController: NavController,
    viewModel: TariffInviteViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        LaunchedEffect(viewModel.shouldShowAlert) {
            if (viewModel.shouldShowAlert) {
                delay(3000L)
                viewModel.stopAlert()
            }
        }
        LaunchedEffect(viewModel.shouldMoveToMain) {
            if (viewModel.shouldMoveToMain) {
                delay(1000L)
                navController.navigate(NavigationItem.CreateGame.route) {
                    popUpTo(0)
                }
            }
        }
        LogoLabel(text = viewModel.inviteText ?: "Ошибка загрузки приглашения")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GradientFilledButton(
                text = viewModel.acceptButtonText,
                enabled = viewModel.acceptButtonEnabled,
                onClick = { viewModel.accept() }
            )
            OutlinedGradientButton(
                text = "Отклонить",
                onClick = {
                    navController.navigate(NavigationItem.CreateGame.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        if (viewModel.shouldShowAlert) {
            SelfHidingBottomAlert(viewModel.alertText)
        }
    }
}