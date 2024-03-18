package ru.hse.connecteam.features.profile.domain

import android.graphics.Bitmap

data class UserDomainModel(
    val id: String,
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