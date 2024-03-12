package ru.hse.connecteam.features.main.presentation.screens.invite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.hse.connecteam.features.auth.presentation.components.LogoLabel
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert

@Composable
fun InviteScreen(
    navController: NavController,
    viewModel: InviteViewModel = hiltViewModel()
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
        /*LaunchedEffect(viewModel.shouldMoveToGame) {
            if (viewModel.shouldMoveToGame) {
                delay(1000L)
                //navController.navigate(NavigationItem.Profile.route)
            }
        }*/
        LogoLabel(text = viewModel.inviteText)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BaseOutlinedTextInput(
                viewModel.name,
                onValueChanged = { },
                label = "Имя",
                readOnly = true
            )
            BaseOutlinedTextInput(
                viewModel.surname,
                onValueChanged = { },
                label = "Фамилия",
                readOnly = true
            )
        }

        GradientFilledButton(
            text = viewModel.acceptButtonText,
            enabled = viewModel.acceptButtonEnabled,
            onClick = { viewModel.accept() }
        )

        if (viewModel.shouldShowAlert) {
            SelfHidingBottomAlert(viewModel.alertText)
        }
    }
}