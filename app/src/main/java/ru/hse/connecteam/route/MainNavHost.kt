package ru.hse.connecteam.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import ru.hse.connecteam.features.game.presentation.screens.GameScreen
import ru.hse.connecteam.features.main.presentation.screens.create.CreateScreen
import ru.hse.connecteam.features.main.presentation.screens.gameinvite.InviteScreen
import ru.hse.connecteam.features.main.presentation.screens.gameinviteloading.LoadingGameInviteScreen
import ru.hse.connecteam.features.main.presentation.screens.gamelist.GameListScreen
import ru.hse.connecteam.features.main.presentation.screens.loading.LoadingScreen
import ru.hse.connecteam.features.main.presentation.screens.tariffinvite.TariffInviteScreen
import ru.hse.connecteam.features.main.presentation.screens.tariffinviteloading.LoadingTariffInviteScreen
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
    startDestination: String = NavigationItem.Loading.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Loading.route) {
            LoadingScreen(navController = navController)
        }
        composable(NavigationItem.TariffList.route) {
            TariffListScreen(navController = navController)
        }
        composable("${NavigationItem.TariffPurchase.route}/{tariff}") {
            TariffPurchaseScreen(navController = navController)
        }
        composable(NavigationItem.GameList.route) {
            GameListScreen(navController = navController)
        }
        composable("${NavigationItem.Game.route}/{id}") {
            GameScreen(navController = navController)
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
        composable(
            NavigationItem.GameInviteLoadingAuth.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = "${DeeplinkUri.inviteUriHttps}/game/{invite}" },
                navDeepLink { uriPattern = "${DeeplinkUri.inviteUriHttp}/game/{invite}" }
            )
        ) { backStackEntry ->
            LoadingGameInviteScreen(
                navController = navController,
                backStackEntry.arguments?.getString("invite")
            )
        }
        composable(NavigationItem.TariffInviteLoadingAuth.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = "${DeeplinkUri.inviteUriHttps}/plan/{invite}" },
                navDeepLink { uriPattern = "${DeeplinkUri.inviteUriHttp}/plan/{invite}" }
            )) { backStackEntry ->
            LoadingTariffInviteScreen(
                navController = navController,
                backStackEntry.arguments?.getString("invite")
            )
        }
        composable("${NavigationItem.GameInviteAuth.route}/{invite}/{code}") {
            InviteScreen(navController = navController)
        }
        composable("${NavigationItem.TariffInviteAuth.route}/{invite}/{code}") {
            TariffInviteScreen(navController = navController)
        }
        composable(NavigationItem.CreateGame.route) {
            CreateScreen(navController = navController)
        }
    }
}