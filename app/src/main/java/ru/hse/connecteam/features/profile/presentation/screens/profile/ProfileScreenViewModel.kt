package ru.hse.connecteam.features.profile.presentation.screens.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel

class ProfileScreenViewModel(
    val fullName: String,
    val image: Bitmap?,
) : ViewModel()