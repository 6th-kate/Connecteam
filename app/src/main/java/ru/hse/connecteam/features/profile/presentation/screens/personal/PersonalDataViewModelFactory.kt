package ru.hse.connecteam.features.profile.presentation.screens.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository

class PersonalDataViewModelFactory(
    private val repository: ProfileDataRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val user = repository.getUser()
        return PersonalDataViewModel(
            repository,
            user.image,
            user.firstName,
            user.surname,
            user.about
        ) as T
    }
}
