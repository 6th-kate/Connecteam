package ru.hse.connecteam.features.auth.presentation.screens.gameinvite

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
import ru.hse.connecteam.ui.components.buttons.TextSpanButton
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert

@Composable
fun GameInviteScreen(
    navController: NavController,
    viewModel: GameInviteViewModel = hiltViewModel()
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
        LaunchedEffect(viewModel.shouldMoveToGame) {
            if (viewModel.shouldMoveToGame) {
                delay(1000L)
                //navController.navigate(NavigationItem.Game.route)
            }
        }
        LogoLabel(text = viewModel.inviteText ?: "Ошибка загрузки приглашения")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BaseOutlinedTextInput(
                viewModel.name,
                onValueChanged = { viewModel.updateName(it) },
                label = "Имя"
            )
            BaseOutlinedTextInput(
                viewModel.surname,
                onValueChanged = { viewModel.updateSurname(it) },
                label = "Фамилия"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GradientFilledButton(
                text = viewModel.acceptButtonText,
                enabled = viewModel.acceptButtonEnabled,
                onClick = { viewModel.accept() })
            TextSpanButton(
                textMain = "Есть аккаунт? ",
                textAdditional = "Войти",
                onClick = {
                    navController.navigate(
                        "${NavigationItem.SignIn.route}/${viewModel.inviteText ?: "none"}"
                    ) {
                        popUpTo(0)
                    }
                },
            )
        }

        if (viewModel.shouldShowAlert) {
            SelfHidingBottomAlert(viewModel.alertText)
        }
    }
}