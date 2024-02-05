package ru.hse.connecteam.features.profile.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.hse.connecteam.features.auth.data.ServerAuthRepository
import ru.hse.connecteam.features.auth.presentation.screens.verify.VerificationViewModel
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository

class ProfileScreenViewModelFactory(
    private val repository: ProfileDataRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val user = repository.getUser()
        return ProfileScreenViewModel(
            user.firstName + " " + user.surname,
            image = null
        ) as T
    }
}
