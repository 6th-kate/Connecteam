package ru.hse.connecteam.features.main.presentation.screens.loading

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.main.domain.GameStaticRepository
import ru.hse.connecteam.route.NavigationItem
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val repository: GameStaticRepository
) : ViewModel() {
    var movePath by mutableStateOf("")
        private set
    var shouldMove by mutableStateOf(false)
        private set

    var initialized by mutableStateOf(false)
        private set

    init {
        if (!initialized) {
            viewModelScope.launch {
                val gameInvite = repository.checkGameInvite()
                val tariffInvite = repository.checkGameInvite()

                if (gameInvite.isNullOrEmpty() && tariffInvite.isNullOrEmpty()) {
                    movePath = NavigationItem.CreateGame.route
                    shouldMove = true
                }

                if (!gameInvite.isNullOrEmpty()) {
                    viewModelScope.launch {
                        val game = repository.verifyGameInvite(gameInvite)
                        movePath = if (game != null) {
                            "${NavigationItem.GameInviteAuth.route}/" +
                                    "Вас приглашают присоединиться к игре " +
                                    "${game.name.replace("/", "")}/" +
                                    "$gameInvite"
                        } else {
                            "${NavigationItem.GameInviteAuth.route}/none/none"
                        }
                        shouldMove = true
                    }
                }
                if (!tariffInvite.isNullOrEmpty()) {
                    viewModelScope.launch {
                        val owner = repository.verifyTariffInvite(tariffInvite)
                        movePath = if (owner != null) {
                            "${NavigationItem.TariffInviteAuth.route}/" +
                                    "Пользователь ${
                                        owner.ownerFullName.replace(
                                            "/",
                                            ""
                                        )
                                    } приглашает Вас присоединиться к тарифу/" +
                                    "$gameInvite"
                        } else {
                            "${NavigationItem.TariffInviteAuth.route}/none/none"
                        }
                        shouldMove = true
                    }
                }
                initialized = true
            }
        }
    }
}