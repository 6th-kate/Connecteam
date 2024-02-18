package ru.hse.connecteam.features.profile.domain

import ru.hse.connecteam.shared.models.ResponseInfo
import ru.hse.connecteam.shared.models.TariffModel

interface ProfileDataRepository {
    suspend fun getUser(): UserDomainModel?
    fun tryChangePassword(oldPassword: String, newPassword: String)
    suspend fun getTariff(): TariffModel?
    suspend fun editPersonalData(name: String, surname: String, description: String): ResponseInfo
    suspend fun editCompanyData(name: String, url: String, description: String): ResponseInfo
    suspend fun logOut(): ResponseInfo
    suspend fun changePassword(oldPassword: String, newPassword: String): ResponseInfo
    suspend fun sendChangeEmailCode(newEmail: String, password: String): ResponseInfo
    suspend fun confirmEmailChange(newEmail: String, code: String): ResponseInfo
}