package ru.hse.connecteam.features.profile.domain

import ru.hse.connecteam.shared.models.TariffModel

interface ProfileDataRepository {
    fun getUser(): UserDomainModel
    fun tryChangePassword(oldPassword: String, newPassword: String)
    fun getTariff(): TariffModel?
}