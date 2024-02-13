package ru.hse.connecteam.features.profile.presentation.components.datascreen

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.shared.utils.NAME_REGEX

abstract class GenericDataViewModel(
    initImage: Bitmap? = null,
    initFirstField: String = "Загружаем...",
    initSecondField: String = "Загружаем...",
    initAbout: String = "Загружаем..."
) : ViewModel() {
    abstract val screenTitle: String
    abstract val firstFieldLabel: String
    abstract val secondFieldLabel: String
    abstract val aboutLabel: String
    abstract fun onSave()

    var firstField by mutableStateOf(initFirstField)
        protected set

    var secondField by mutableStateOf(initSecondField)
        protected set

    var about by mutableStateOf(initAbout)
        protected set

    var image by mutableStateOf(initImage)
        protected set

    var saveEnabled by mutableStateOf(false)
        protected set

    var saveButtonText by mutableStateOf("Сохранить")
        protected set

    open fun updateFirstField(input: String) {
        firstField = input
        saveEnabled = true
    }

    open fun updateSecondField(input: String) {
        secondField = input
        saveEnabled = true
    }

    fun updateAbout(input: String) {
        about = input
        saveEnabled = true
    }

    fun updateImage(newImage: Bitmap?) {
        image = newImage
        saveEnabled = true
    }

    fun onImageError(e: Exception?) {
        TODO("show alert")
    }
}