package ru.hse.connecteam.features.profile.presentation.screens.account.email.verify

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.hse.connecteam.ui.components.bars.TransparentAppBar
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.components.inputs.OtpTextField
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun VerifyEmailChangeScreen(
    viewModel: VerifyEmailChangeViewModel = hiltViewModel(),
    navController: NavController,
) {
    Scaffold(topBar = {
        TransparentAppBar(
            title = "Введите код",
            navController = navController
        )
    }) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 15.dp)
        ) {
            LaunchedEffect(viewModel.shouldShowAlert || viewModel.shouldReturn) {
                if (viewModel.shouldShowAlert) {
                    delay(1000L)
                    viewModel.stopAlert()
                }
                if (viewModel.shouldReturn) {
                    navController.popBackStack()
                }
            }
            LaunchedEffect(viewModel.resendTimerIsTicking) {
                while (viewModel.resendTimerIsTicking) {
                    delay(1000L)
                    viewModel.tickOrStop()
                }
            }
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
}

@Preview
@Composable
fun VerifyEmailChangeScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //EmailChangeScreen(navController = rememberNavController())
        }
    }
}