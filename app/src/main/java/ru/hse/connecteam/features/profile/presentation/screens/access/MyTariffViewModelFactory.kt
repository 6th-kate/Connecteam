package ru.hse.connecteam.features.profile.presentation.screens.access

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository

class MyTariffViewModelFactory(
    private val repository: ProfileDataRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val tariff = repository.getTariff()
        return MyTariffViewModel(tariff) as T
    }
}
