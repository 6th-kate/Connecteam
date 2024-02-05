package ru.hse.connecteam.features.profile.presentation.screens.account.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository

class EmailChangeViewModelFactory(
    private val repository: ProfileDataRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val user = repository.getUser()
        return EmailChangeViewModel(repository, user.email) as T
    }
}
