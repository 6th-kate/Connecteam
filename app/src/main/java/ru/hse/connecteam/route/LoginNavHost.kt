package ru.hse.connecteam.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.hse.connecteam.features.auth.presentation.screens.signin.SignInScreen
import ru.hse.connecteam.features.auth.presentation.screens.signup.SignUpScreen
import ru.hse.connecteam.features.auth.presentation.screens.verify.VerificationScreen

@Composable
fun LoginNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.SignIn.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(NavigationItem.SignIn.route) {
            SignInScreen(navController = navController)
        }
        composable("${NavigationItem.Verification.route}/{username}/{password}/{id}") {
            VerificationScreen(navController = navController)
        }
    }
}