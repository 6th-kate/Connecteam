package ru.hse.connecteam.features.profile.domain

import kotlinx.coroutines.flow.Flow
import ru.hse.connecteam.shared.models.response.ResponseInfo

interface ProfileDataRepository {
    suspend fun getUser(): UserDomainModel?
    suspend fun getUserFlow(): Flow<UserDomainModel?>
    suspend fun getTariff(): TariffDomainModel?
    suspend fun editPersonalData(name: String, surname: String, description: String): ResponseInfo
    suspend fun editCompanyData(name: String, url: String, description: String): ResponseInfo
    suspend fun logOut(): ResponseInfo
    suspend fun changePassword(oldPassword: String, newPassword: String): ResponseInfo
    suspend fun sendChangeEmailCode(newEmail: String, password: String): ResponseInfo
    suspend fun confirmEmailChange(newEmail: String, code: String): ResponseInfo
}