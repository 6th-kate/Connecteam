package ru.hse.connecteam.shared.models

import android.graphics.Bitmap

data class UserModel(
    val firstName: String,
    val surname: String,
    val image: Bitmap?,
    val email: String,
    val about: String,
    val companyLogo: Bitmap?,
    val companyName: String,
    val companySite: String,
    val companyAbout: String,
)