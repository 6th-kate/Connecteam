package ru.hse.connecteam.features.auth.presentation.screens.signup

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import ru.hse.connecteam.features.auth.presentation.components.LogoLabel
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.TextSpanButton
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.components.inputs.PasswordTextInput
import ru.hse.connecteam.ui.components.inputs.PhoneEmailTextInput
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        LaunchedEffect(viewModel.moveToVerification) {
            if (viewModel.moveToVerification) {
                delay(1000L)
                navController.navigate(
                    "${NavigationItem.Verification.route}/${viewModel.getVerificationParameters()}"
                )
            }
        }
        LaunchedEffect(viewModel.shouldShowAlert) {
            if (viewModel.shouldShowAlert) {
                delay(1000L)
                viewModel.stopAlert()
            }
        }
        LogoLabel(text = viewModel.inviteText ?: "Регистрация")
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
            PhoneEmailTextInput(
                text = viewModel.username,
                onValueChanged = { viewModel.updateUsername(it) },
                error = "некорректный формат",
                isError = viewModel.usernameError,
            )
            PasswordTextInput(
                viewModel.password,
                onValueChanged = { viewModel.updatePassword(it) },
                error = "недостаточно надёжный пароль",
                isError = viewModel.passwordError,
                label = "Придумайте пароль"
            )
            PasswordTextInput(
                viewModel.passwordRepeat,
                onValueChanged = { viewModel.updatePasswordRepeat(it) },
                error = "пароли не совпадают",
                isError = viewModel.passwordRepeatError,
                enabled = viewModel.passwordRepeatEnabled,
                label = "Подтвердите пароль"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GradientFilledButton(
                text = viewModel.singUpButtonText,
                enabled = viewModel.signUpButtonEnabled,
                onClick = { viewModel.signUp() })
            TextSpanButton(textMain = "У вас есть аккаунт? ", textAdditional = "Войти", onClick = {
                navController.navigate(
                    "${NavigationItem.SignIn.route}/${viewModel.inviteText ?: "none"}"
                ) {
                    popUpTo(0)
                }
            })
        }
        if (viewModel.shouldShowAlert) {
            SelfHidingBottomAlert(viewModel.alertText)
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            SignUpScreen(navController = rememberNavController())
        }
    }
}