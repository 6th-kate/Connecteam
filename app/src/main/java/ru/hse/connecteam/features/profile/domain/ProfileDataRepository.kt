package ru.hse.connecteam.features.profile.domain

import kotlinx.coroutines.flow.Flow
import ru.hse.connecteam.shared.models.ResponseInfo
import ru.hse.connecteam.shared.models.TariffModel

interface ProfileDataRepository {
    suspend fun getUser(): UserDomainModel?
    suspend fun getUserFlow(): Flow<UserDomainModel?>
    suspend fun getTariff(): TariffModel?
    suspend fun editPersonalData(name: String, surname: String, description: String): ResponseInfo
    suspend fun editCompanyData(name: String, url: String, description: String): ResponseInfo
    suspend fun logOut(): ResponseInfo
    suspend fun changePassword(oldPassword: String, newPassword: String): ResponseInfo
    suspend fun sendChangeEmailCode(newEmail: String, password: String): ResponseInfo
    suspend fun confirmEmailChange(newEmail: String, code: String): ResponseInfo
}