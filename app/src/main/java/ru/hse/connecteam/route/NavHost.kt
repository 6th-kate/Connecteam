package ru.hse.connecteam.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.hse.connecteam.Greeting
import ru.hse.connecteam.features.auth.data.ServerAuthRepository
import ru.hse.connecteam.features.auth.presentation.screens.signin.SignInScreen
import ru.hse.connecteam.features.auth.presentation.screens.signin.SignInViewModel
import ru.hse.connecteam.features.auth.presentation.screens.signup.SignUpScreen
import ru.hse.connecteam.features.auth.presentation.screens.signup.SignUpViewModel
import ru.hse.connecteam.features.auth.presentation.screens.verify.VerificationScreen
import ru.hse.connecteam.features.auth.presentation.screens.verify.VerificationViewModel
import ru.hse.connecteam.features.auth.presentation.screens.verify.VerificationViewModelFactory
import ru.hse.connecteam.features.profile.data.ServerProfileRepository
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.presentation.screens.access.AccessScreen
import ru.hse.connecteam.features.profile.presentation.screens.access.MyTariffViewModel
import ru.hse.connecteam.features.profile.presentation.screens.account.AccountScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.AccountScreenViewModel
import ru.hse.connecteam.features.profile.presentation.screens.account.email.EmailChangeScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.email.EmailChangeViewModel
import ru.hse.connecteam.features.profile.presentation.screens.account.password.PasswordChangeScreen
import ru.hse.connecteam.features.profile.presentation.screens.account.password.PasswordChangeViewModel
import ru.hse.connecteam.features.profile.presentation.screens.company.CompanyDataScreen
import ru.hse.connecteam.features.profile.presentation.screens.company.CompanyDataViewModel
import ru.hse.connecteam.features.profile.presentation.screens.personal.PersonalDataScreen
import ru.hse.connecteam.features.profile.presentation.screens.personal.PersonalDataViewModel
import ru.hse.connecteam.features.profile.presentation.screens.profile.ProfileScreen
import ru.hse.connecteam.features.profile.presentation.screens.profile.ProfileScreenViewModel

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
            val r = ServerAuthRepository()
            val vm = SignUpViewModel(r)
            SignUpScreen(vm, navController = navController)
        }
        composable(NavigationItem.SignIn.route) {
            val r = ServerAuthRepository()
            val vm = SignInViewModel(r)
            SignInScreen(vm, navController = navController)
        }
        composable("${NavigationItem.Verification.route}/{username}/{password}/{id}") { backStackEntry ->
            val r = ServerAuthRepository()
            val username = backStackEntry.arguments?.getString("username")
            val password = backStackEntry.arguments?.getString("password")
            val id = backStackEntry.arguments?.getString("id")
            VerificationScreen(
                viewModel(
                    factory = VerificationViewModelFactory(
                        r,
                        username ?: "",
                        password ?: "",
                        id ?: ""
                    )
                ), navController = navController
            )
        }
        composable(NavigationItem.Main.route) {
            Greeting(name = "Main Screen Mock")
        }
        navigation(NavigationItem.Profile.route, "profile") {
            composable(NavigationItem.Profile.route) {
                val vm = ProfileScreenViewModel("", null)
                ProfileScreen(vm, navController = navController)
            }
            composable(NavigationItem.Personal.route) {
                val r = ServerProfileRepository()
                val vm = PersonalDataViewModel(r, null, "", "", "")
                PersonalDataScreen(vm)
            }
            composable(NavigationItem.Company.route) {
                val r = ServerProfileRepository()
                val vm = CompanyDataViewModel(r, null, "", "", "")
                CompanyDataScreen(vm)
            }
            composable(NavigationItem.Account.route) {
                val vm = AccountScreenViewModel("")
                AccountScreen(vm, navController = navController)
            }
            composable(NavigationItem.EmailChange.route) {
                val r = ServerProfileRepository()
                val vm = EmailChangeViewModel(r, "")
                EmailChangeScreen(vm, navController = navController)
            }
            composable(NavigationItem.PasswordChange.route) {
                val r = ServerProfileRepository()
                val vm = PasswordChangeViewModel(r)
                PasswordChangeScreen(vm)
            }
            composable(NavigationItem.MyTariff.route) {
                val vm = MyTariffViewModel(null)
                AccessScreen(vm, navController = navController)
            }
        }
    }
}