package ru.hse.connecteam.features.profile.presentation.screens.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
) : ViewModel() {
    private var initialized: Boolean = false
    var fullName: String = "Загружаем..."
    var image: Bitmap? = null

    init {
        if (!initialized) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = repository.getUser()
                CoroutineScope(Dispatchers.Main).launch {
                    fullName = if (user != null) {
                        user.firstName + " " + user.surname
                    } else {
                        "Ошибка"
                    }
                    initialized = true
                }
            }
        }
    }
}