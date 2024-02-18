package ru.hse.connecteam.features.profile.presentation.screens.account.email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import ru.hse.connecteam.features.profile.presentation.components.TransparentAppBar
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.inputs.PasswordTextInput
import ru.hse.connecteam.ui.components.inputs.PhoneEmailTextInput
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun EmailChangeScreen(
    viewModel: EmailChangeViewModel = hiltViewModel(),
    navController: NavController,
) {
    Scaffold(topBar = {
        TransparentAppBar(
            title = "Смена почты",
            navController = navController
        )
    }) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 15.dp)
        ) {
            LaunchedEffect(viewModel.moveToVerification) {
                if (viewModel.moveToVerification) {
                    navController.navigate(
                        "${NavigationItem.VerifyEmailChange.route}/${viewModel.getVerificationParameters()}"
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                PhoneEmailTextInput(
                    text = viewModel.username,
                    error = "Некорректный формат",
                    onValueChanged = { viewModel.updateUsername(it) },
                    isError = viewModel.usernameError
                )// add vm
                PasswordTextInput(text = viewModel.password,
                    label = "Пароль",
                    onValueChanged = { viewModel.updatePassword(it) })// add vm
            }
            GradientFilledButton(text = viewModel.saveButtonText,
                enabled = viewModel.saveEnabled,
                onClick = { viewModel.onSave() })
        }
    }
}

@Preview
@Composable
fun EmailChangeScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //EmailChangeScreen(navController = rememberNavController())
        }
    }
}