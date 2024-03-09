package ru.hse.connecteam.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.hse.connecteam.Greeting
import ru.hse.connecteam.features.profile.presentation.screens.access.AccessScreen
import ru.hse.connecteam.features.profile.presentation.screens.access.participants.TariffParticipantsScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.AccountScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.email.EmailChangeScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.email.verify.VerifyEmailChangeScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.password.PasswordChangeScreen
import ru.hse.connecteam.features.profile.presentation.screens.company.CompanyDataScreen
import ru.hse.connecteam.features.profile.presentation.screens.personal.PersonalDataScreen
import ru.hse.connecteam.features.profile.presentation.screens.profile.ProfileScreen
import ru.hse.connecteam.features.tariffs.presentation.purchase.TariffPurchaseScreen
import ru.hse.connecteam.features.tariffs.presentation.tarifflist.TariffListScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Profile.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Main.route) {
            Greeting(name = "Main Screen Mock")
        }
        composable("${NavigationItem.TariffList.route}/{hasTariff}") {
            TariffListScreen(navController = navController)
        }
        composable("${NavigationItem.TariffPurchase.route}/{tariff}") {
            TariffPurchaseScreen(navController = navController)
        }
        navigation(NavigationItem.Profile.route, "profile") {
            composable(NavigationItem.Profile.route) {
                ProfileScreen(navController = navController)
            }
            composable(NavigationItem.Personal.route) {
                PersonalDataScreen(navController = navController)
            }
            composable(NavigationItem.Company.route) {
                CompanyDataScreen(navController = navController)
            }
            composable(NavigationItem.Account.route) {
                AccountScreen(navController = navController)
            }
            composable(NavigationItem.EmailChange.route) {
                EmailChangeScreen(navController = navController)
            }
            composable("${NavigationItem.VerifyEmailChange.route}/{username}/{password}") {
                VerifyEmailChangeScreen(navController = navController)
            }
            composable(NavigationItem.PasswordChange.route) {
                PasswordChangeScreen(navController = navController)
            }
            composable(NavigationItem.MyTariff.route) {
                AccessScreen(navController = navController)
            }
            composable(NavigationItem.TariffParticipants.route) {
                TariffParticipantsScreen(navController = navController)
            }
        }
    }
}