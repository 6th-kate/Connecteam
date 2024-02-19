package ru.hse.connecteam.features.profile.presentation.screens.profile

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
) : ViewModel() {
    var fullName: String by mutableStateOf("Загружаем...")
        private set
    var image: Bitmap? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            repository.getUserFlow().collectLatest { user ->
                fullName = if (user != null) {
                    user.firstName + " " + user.surname
                } else {
                    "Ошибка"
                }
            }
        }
    }
}