package ru.hse.connecteam.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.hse.connecteam.Greeting
import ru.hse.connecteam.features.auth.presentation.screens.signin.SignInScreen
import ru.hse.connecteam.features.auth.presentation.screens.signup.SignUpScreen
import ru.hse.connecteam.features.auth.presentation.screens.verify.VerificationScreen
import ru.hse.connecteam.features.profile.presentation.screens.access.AccessScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.AccountScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.email.EmailChangeScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.password.PasswordChangeScreen
import ru.hse.connecteam.features.profile.presentation.screens.company.CompanyDataScreen
import ru.hse.connecteam.features.profile.presentation.screens.personal.PersonalDataScreen
import ru.hse.connecteam.features.profile.presentation.screens.profile.ProfileScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.SignUp.route,
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
        composable(NavigationItem.Main.route) {
            Greeting(name = "Main Screen Mock")
        }
        navigation(NavigationItem.Profile.route, "profile") {
            composable(NavigationItem.Profile.route) {
                ProfileScreen(navController = navController)
            }
            composable(NavigationItem.Personal.route) {
                PersonalDataScreen()
            }
            composable(NavigationItem.Company.route) {
                CompanyDataScreen()
            }
            composable(NavigationItem.Account.route) {
                AccountScreen(navController = navController)
            }
            composable(NavigationItem.EmailChange.route) {
                EmailChangeScreen(navController = navController)
            }
            composable(NavigationItem.PasswordChange.route) {
                PasswordChangeScreen()
            }
            composable(NavigationItem.MyTariff.route) {
                AccessScreen(navController = navController)
            }
        }
    }
}