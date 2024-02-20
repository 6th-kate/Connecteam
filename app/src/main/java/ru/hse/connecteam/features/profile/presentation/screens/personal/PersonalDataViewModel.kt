package ru.hse.connecteam.features.profile.presentation.screens.personal

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.presentation.components.datascreen.GenericDataViewModel
import ru.hse.connecteam.shared.models.response.StatusInfo
import ru.hse.connecteam.shared.utils.NAME_REGEX
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
) : GenericDataViewModel() {
    init {
        viewModelScope.launch {
            repository.getUserFlow().collectLatest { user ->
                if (user != null) {
                    firstField = user.firstName
                    secondField = user.surname
                    about = user.about
                } else {
                    firstField = ""
                    secondField = ""
                    about = ""
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
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.editPersonalData(firstField, secondField, about)
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.status == StatusInfo.OK) {
                        alertText = "Данные успешно сохранены"
                    } else {
                        saveEnabled = true
                        alertText = "Ошибка сохранения"
                    }
                    shouldShowAlert = true
                    saveButtonText = "Сохранить"
                }
            }
        }
    }
}