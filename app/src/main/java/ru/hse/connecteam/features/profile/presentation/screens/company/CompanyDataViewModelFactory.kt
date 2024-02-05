package ru.hse.connecteam.features.profile.presentation.screens.company


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.domain.UserDomainModel

class CompanyDataViewModelFactory(
    private val repository: ProfileDataRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val user : UserDomainModel = repository.getUser()
        return CompanyDataViewModel(
            repository,
            user.companyLogo,
            user.companyName,
            user.companySite,
            user.companyAbout
        ) as T
    }
}
