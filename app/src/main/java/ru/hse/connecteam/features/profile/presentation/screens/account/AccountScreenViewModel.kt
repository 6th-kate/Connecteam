package ru.hse.connecteam.features.profile.presentation.screens.account

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val repository: ProfileDataRepository
) : ViewModel() {
    private var initialized: Boolean = false
    var email: String = "Загружаем..."

    init {
        if (!initialized) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = repository.getUser()
                CoroutineScope(Dispatchers.Main).launch {
                    email = user?.email ?: "Ошибка"
                    initialized = true
                }
            }
        }
    }
}