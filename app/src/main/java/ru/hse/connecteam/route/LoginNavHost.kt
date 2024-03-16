package ru.hse.connecteam.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import ru.hse.connecteam.features.auth.presentation.screens.gameinvite.GameInviteScreen
import ru.hse.connecteam.features.auth.presentation.screens.loadinggameinvite.LoadingGameInviteScreen
import ru.hse.connecteam.features.auth.presentation.screens.loadingtariffinvite.LoadingTariffInviteScreen
import ru.hse.connecteam.features.auth.presentation.screens.signin.SignInScreen
import ru.hse.connecteam.features.auth.presentation.screens.signup.SignUpScreen
import ru.hse.connecteam.features.auth.presentation.screens.verify.VerificationScreen

@Composable
fun LoginNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "${NavigationItem.SignIn.route}/none",
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("${NavigationItem.SignUp.route}/{invite}") {
            SignUpScreen(navController = navController)
        }
        composable("${NavigationItem.SignIn.route}/{invite}") {
            SignInScreen(navController = navController)
        }
        composable("${NavigationItem.GameInviteUnauth.route}/{invite}") {
            GameInviteScreen(navController = navController)
        }
        composable("${NavigationItem.Verification.route}/{username}/{password}/{id}") {
            VerificationScreen(navController = navController)
        }
        composable(
            NavigationItem.GameInviteLoadingUnauth.route,
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
        composable(NavigationItem.TariffInviteLoadingUnauth.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = "${DeeplinkUri.inviteUriHttps}/plan/{invite}" },
                navDeepLink { uriPattern = "${DeeplinkUri.inviteUriHttp}/plan/{invite}" }
            )) { backStackEntry ->
            LoadingTariffInviteScreen(
                navController = navController,
                backStackEntry.arguments?.getString("invite")
            )
        }
    }
}