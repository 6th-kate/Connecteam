package ru.hse.connecteam.features.auth.presentation.screens.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import ru.hse.connecteam.features.auth.data.ServerAuthRepository
import ru.hse.connecteam.features.auth.presentation.components.LogoLabel
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.TextSpanButton
import ru.hse.connecteam.ui.components.inputs.PasswordTextInput
import ru.hse.connecteam.ui.components.inputs.PhoneEmailTextInput
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,// = viewModel(),
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        LaunchedEffect(viewModel.shouldShowAlert) {
            if (viewModel.shouldShowAlert) {
                delay(1000L)
                viewModel.stopAlert()
            }
        }
        LaunchedEffect(viewModel.shouldMoveToMain) {
            if (viewModel.shouldMoveToMain) {
                delay(1000L)
                navController.navigate(NavigationItem.Main.route)
            }
        }
        LogoLabel(text = "Вход")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PhoneEmailTextInput(
                text = viewModel.username,
                onValueChanged = { viewModel.updateUsername(it) },
                error = "некорректный формат",
                isError = viewModel.usernameError
            )
            PasswordTextInput(
                text = viewModel.password,
                onValueChanged = { viewModel.updatePassword(it) },
                label = "Введите пароль",
                hasSmallClickable = true,
                smallClickableText = "Забыли пароль?"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GradientFilledButton(
                text = viewModel.singInButtonText,
                enabled = viewModel.signInButtonEnabled,
                onClick = { viewModel.signIn() })
            TextSpanButton(
                textMain = "Нет аккаунта? ",
                textAdditional = "Зарегистрироваться",
                onClick = {
                    navController.navigate(NavigationItem.SignUp.route) {
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


@Preview
@Composable
fun SignInScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val r = ServerAuthRepository()
            val vm = SignInViewModel(r)
            SignInScreen(vm, navController = rememberNavController())
        }
    }
}