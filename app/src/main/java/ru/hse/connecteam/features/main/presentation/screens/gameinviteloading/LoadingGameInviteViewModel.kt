package ru.hse.connecteam.features.main.presentation.screens.gameinviteloading

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.main.domain.GameStaticRepository
import javax.inject.Inject

@HiltViewModel
class LoadingGameInviteViewModel @Inject constructor(
    private val repository: GameStaticRepository,
) : ViewModel() {
    var isLoading by mutableStateOf(true)
        private set
    var signInParams by mutableStateOf("")
        private set
    var error: String? by mutableStateOf(null)
        private set

    fun loadFromInviteCode(inviteCode: String?) {
        if (isLoading) {
            if (inviteCode != null) {
                viewModelScope.launch {
                    val game = repository.verifyGameInvite(inviteCode)
                    if (game != null) {
                        signInParams =
                            "Вас приглашают присоединиться к игре ${
                                game.name.replace(
                                    "/",
                                    ""
                                )
                            }/${inviteCode}"
                    } else {
                        signInParams = "none/none"
                        error = "Ошибка загрузки приглашения"
                    }
                    isLoading = false
                }
            } else {
                error = "Ошибка загрузки приглашения"
                signInParams = "none"
                isLoading = false
            }
        }
    }
}