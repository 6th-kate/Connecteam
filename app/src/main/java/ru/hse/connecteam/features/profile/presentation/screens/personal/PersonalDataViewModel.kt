package ru.hse.connecteam.features.profile.presentation.screens.personal

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.presentation.components.datascreen.GenericDataViewModel
import ru.hse.connecteam.shared.utils.NAME_REGEX
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
) : GenericDataViewModel() {
    private var initialized: Boolean = false

    init {
        if (!initialized) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = repository.getUser()
                CoroutineScope(Dispatchers.Main).launch {
                    if (user != null) {
                        firstField = user.firstName
                        secondField = user.surname
                        about = user.about
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

    override val screenTitle: String = "Личные данные"
    override val firstFieldLabel: String = "Имя"
    override val secondFieldLabel: String = "Фамилия"
    override val aboutLabel: String = "О себе"

    override fun updateFirstField(input: String) {
        firstField = input.replace(NAME_REGEX, "")
        saveEnabled = true
    }

    override fun updateSecondField(input: String) {
        secondField = input.replace(NAME_REGEX, "")
        saveEnabled = true
    }

    override fun onSave() {
        if (saveEnabled) {
            saveButtonText = "Сохраняем..."
            saveEnabled = false
            TODO("add password change request, then show popup and enable button")
        }
    }
}