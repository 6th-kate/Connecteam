package ru.hse.connecteam.features.profile.presentation.screens.account.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.connecteam.features.profile.presentation.components.TransparentAppBar
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.inputs.PasswordTextInput
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun PasswordChangeScreen(
    viewModel: PasswordChangeViewModel = hiltViewModel(),
    navController: NavController,
) {
    Scaffold(topBar = {
        TransparentAppBar(
            title = "Смена пароля",
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                PasswordTextInput(text = viewModel.oldPassword,
                    label = "Введите текущий пароль",
                    onValueChanged = { viewModel.updateOldPassword(it) })
                PasswordTextInput(text = viewModel.newPassword,
                    label = "Введите новый пароль",
                    isError = viewModel.newPasswordError,
                    error = "Недостаточно надёжный пароль",
                    onValueChanged = { viewModel.updateNewPassword(it) })
                PasswordTextInput(text = viewModel.newPasswordRepeat,
                    label = "Повторите новый пароль",
                    error = "Пароли не совпадают",
                    enabled = viewModel.newPasswordRepeatEnabled,
                    isError = viewModel.newPasswordRepeatError,
                    onValueChanged = { viewModel.updatePasswordRepeat(it) })
            }
            GradientFilledButton(text = viewModel.saveButtonText,
                enabled = viewModel.saveEnabled,
                onClick = { viewModel.onSave() })
        }
    }
}

@Preview
@Composable
fun PasswordChangeScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //PasswordChangeScreen(viewModel = )
        }
    }
}