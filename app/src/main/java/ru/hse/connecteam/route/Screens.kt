package ru.hse.connecteam.route

enum class Screen {
    GAME_INVITE_LOADING_UNAUTH,
    GAME_INVITE_UNAUTH,
    TARIFF_INVITE_LOADING_UNAUTH,
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
    TARIFF_LIST,
    TARIFF_PURCHASE,
    TARIFF_PARTICIPANTS,
}

object DeeplinkUri {
    const val inviteUriHttp = "http://connecteam.ru/invite"
    const val inviteUriHttps = "https://connecteam.ru/invite"
}

sealed class NavigationItem(val route: String) {
    data object GameInviteLoadingUnauth : NavigationItem(Screen.GAME_INVITE_LOADING_UNAUTH.name)
    data object GameInviteUnauth : NavigationItem(Screen.GAME_INVITE_UNAUTH.name)
    data object TariffInviteLoadingUnauth : NavigationItem(Screen.TARIFF_INVITE_LOADING_UNAUTH.name)
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
    data object TariffList : NavigationItem(Screen.TARIFF_LIST.name)
    data object TariffPurchase : NavigationItem(Screen.TARIFF_PURCHASE.name)
    data object TariffParticipants : NavigationItem(Screen.TARIFF_PARTICIPANTS.name)
}