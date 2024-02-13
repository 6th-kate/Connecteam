package ru.hse.connecteam.features.profile.domain

import ru.hse.connecteam.shared.models.TariffModel

interface ProfileDataRepository {
    suspend fun getUser(): UserDomainModel?
    fun tryChangePassword(oldPassword: String, newPassword: String)
    suspend fun getTariff(): TariffModel?
}