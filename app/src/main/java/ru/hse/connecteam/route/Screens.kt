package ru.hse.connecteam.route

enum class Screen {
    SIGN_IN,
    SIGN_UP,
    VERIFICATION,
    MAIN,
    PROFILE,
    PERSONAL,
    COMPANY,
    ACCOUNT,
    PASSWORD_CHANGE,
    EMAIL_CHANGE,
    VERIFY_EMAIL_CHANGE,
    MY_TARIFF,
}

sealed class NavigationItem(val route: String) {
    data object SignIn : NavigationItem(Screen.SIGN_IN.name)
    data object SignUp : NavigationItem(Screen.SIGN_UP.name)
    data object Verification : NavigationItem(Screen.VERIFICATION.name)
    data object Main : NavigationItem(Screen.MAIN.name)
    data object Profile : NavigationItem(Screen.PROFILE.name)
    data object Personal : NavigationItem(Screen.PERSONAL.name)
    data object Company : NavigationItem(Screen.COMPANY.name)
    data object Account : NavigationItem(Screen.ACCOUNT.name)
    data object PasswordChange : NavigationItem(Screen.PASSWORD_CHANGE.name)
    data object EmailChange : NavigationItem(Screen.EMAIL_CHANGE.name)
    data object VerifyEmailChange : NavigationItem(Screen.VERIFY_EMAIL_CHANGE.name)
    data object MyTariff : NavigationItem(Screen.MY_TARIFF.name)
}