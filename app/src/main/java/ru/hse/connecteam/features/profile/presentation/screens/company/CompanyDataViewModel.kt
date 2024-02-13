package ru.hse.connecteam.features.profile.presentation.screens.company

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.presentation.components.datascreen.GenericDataViewModel
import javax.inject.Inject

@HiltViewModel
class CompanyDataViewModel @Inject constructor(
    repository: ProfileDataRepository,
) : GenericDataViewModel() {
    private var initialized: Boolean = false

    init {
        if (!initialized) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = repository.getUser()
                CoroutineScope(Dispatchers.Main).launch {
                    if (user != null) {
                        firstField = user.companyName
                        secondField = user.companySite
                        about = user.companyAbout
                    } else {
                        firstField = ""
                        secondField = ""
                        about = ""
                    }
                    initialized = true
                }
            }
        }
    }


    override val screenTitle: String = "О компании"
    override val firstFieldLabel: String = "Название"
    override val secondFieldLabel: String = "Сайт"
    override val aboutLabel: String = "Описание"

    override fun onSave() {
        if (saveEnabled) {
            saveEnabled = false
            saveButtonText = "Сохраняем..."
        }
    }
}