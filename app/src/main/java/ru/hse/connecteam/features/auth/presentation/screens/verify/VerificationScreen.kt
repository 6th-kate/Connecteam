package ru.hse.connecteam.features.auth.presentation.screens.verify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import ru.hse.connecteam.features.auth.data.ServerAuthRepository
import ru.hse.connecteam.features.auth.presentation.components.LogoLabel
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.components.inputs.OtpTextField
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun VerificationScreen(
    viewModel: VerificationViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LaunchedEffect(viewModel.shouldShowAlert) {
            if (viewModel.shouldShowAlert) {
                delay(1000L)
                viewModel.stopAlert()
                if (viewModel.shouldReturn) {
                    navController.popBackStack()
                }
            }
        }
        LaunchedEffect(viewModel.shouldMoveToMain) {
            if (viewModel.shouldMoveToMain) {
                delay(1000L)
                navController.navigate(NavigationItem.Profile.route)
            }
        }
        LaunchedEffect(viewModel.resendTimerIsTicking) {
            while (viewModel.resendTimerIsTicking) {
                delay(1000L)
                viewModel.tickOrStop()
            }
        }
        LogoLabel(text = "Введите код")
        Spacer(modifier = Modifier.height(129.dp))
        OtpTextField(
            otpText = viewModel.code,
            onOtpTextChange = { input, isFilled -> viewModel.updateCode(input, isFilled) })
        Spacer(modifier = Modifier.height(68.dp))
        OutlinedGradientButton(
            text = viewModel.sendCodeButtonText,
            enabled = viewModel.sendCodeButtonEnabled,
            onClick = { viewModel.resendCode() }
        )
        if (viewModel.shouldShowAlert) {
            SelfHidingBottomAlert(viewModel.alertText)
        }
    }
}

@Preview
@Composable
fun VerificationScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            VerificationScreen(navController = rememberNavController())
        }
    }
}