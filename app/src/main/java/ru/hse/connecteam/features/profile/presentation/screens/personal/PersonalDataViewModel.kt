package ru.hse.connecteam.features.profile.presentation.screens.personal

import android.graphics.Bitmap
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.presentation.components.datascreen.GenericDataViewModel
import ru.hse.connecteam.shared.utils.NAME_REGEX

class PersonalDataViewModel(
    repository: ProfileDataRepository,
    initImage: Bitmap?,
    initName: String,
    initSurname: String,
    initAbout: String
) : GenericDataViewModel(initImage, initName, initSurname, initAbout) {
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