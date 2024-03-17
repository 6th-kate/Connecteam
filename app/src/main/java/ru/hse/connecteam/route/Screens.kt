package ru.hse.connecteam.route

enum class Screen {
    GAME_INVITE_LOADING_UNAUTH,
    GAME_INVITE_UNAUTH,
    TARIFF_INVITE_LOADING_UNAUTH,
    GAME_INVITE_LOADING_AUTH,
    GAME_INVITE_AUTH,
    TARIFF_INVITE_LOADING_AUTH,
    TARIFF_INVITE_AUTH,
    LOADING,
    SIGN_IN,
    SIGN_UP,
    VERIFICATION,
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
    CREATE_GAME
}

object DeeplinkUri {
    const val inviteUriHttp = "http://connecteam.ru/invite"
    const val inviteUriHttps = "https://connecteam.ru/invite"
}

sealed class NavigationItem(val route: String) {
    data object Loading : NavigationItem(Screen.LOADING.name)
    data object GameInviteLoadingAuth : NavigationItem(Screen.GAME_INVITE_LOADING_AUTH.name)
    data object GameInviteAuth : NavigationItem(Screen.GAME_INVITE_AUTH.name)
    data object TariffInviteLoadingAuth : NavigationItem(Screen.TARIFF_INVITE_LOADING_AUTH.name)
    data object TariffInviteAuth : NavigationItem(Screen.TARIFF_INVITE_AUTH.name)
    data object CreateGame : NavigationItem(Screen.CREATE_GAME.name)
    data object GameInviteLoadingUnauth : NavigationItem(Screen.GAME_INVITE_LOADING_UNAUTH.name)
    data object GameInviteUnauth : NavigationItem(Screen.GAME_INVITE_UNAUTH.name)
    data object TariffInviteLoadingUnauth : NavigationItem(Screen.TARIFF_INVITE_LOADING_UNAUTH.name)
    data object SignIn : NavigationItem(Screen.SIGN_IN.name)
    data object SignUp : NavigationItem(Screen.SIGN_UP.name)
    data object Verification : NavigationItem(Screen.VERIFICATION.name)
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