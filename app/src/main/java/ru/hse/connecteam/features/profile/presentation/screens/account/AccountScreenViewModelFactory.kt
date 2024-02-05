package ru.hse.connecteam.features.profile.presentation.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository

class AccountScreenViewModelFactory(
    private val repository: ProfileDataRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val user = repository.getUser()
        return AccountScreenViewModel(user.email) as T
    }
}
