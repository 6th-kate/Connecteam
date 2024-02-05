package ru.hse.connecteam.features.profile.presentation.screens.company

import android.graphics.Bitmap
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.presentation.components.datascreen.GenericDataViewModel

class CompanyDataViewModel(
    repository: ProfileDataRepository,
    initImage: Bitmap?,
    initCompanyTitle: String,
    initCompanySite: String,
    initAbout: String,
) : GenericDataViewModel(initImage, initCompanyTitle, initCompanySite, initAbout) {
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