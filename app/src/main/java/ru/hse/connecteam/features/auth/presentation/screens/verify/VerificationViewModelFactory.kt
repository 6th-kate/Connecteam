package ru.hse.connecteam.features.auth.presentation.screens.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.hse.connecteam.features.auth.data.ServerAuthRepository

class VerificationViewModelFactory(
    private val repository: ServerAuthRepository,
    private val email: String,
    private val password: String,
    private val id: String,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        VerificationViewModel(repository, email, password, id) as T
}

