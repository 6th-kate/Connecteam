package ru.hse.connecteam.features.profile.data

import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.domain.UserDomainModel
import ru.hse.connecteam.shared.models.TariffModel
import javax.inject.Inject

class ServerProfileRepository @Inject constructor() : ProfileDataRepository{
    override fun getUser(): UserDomainModel {
        TODO("Not yet implemented")
    }

    override fun tryChangePassword(oldPassword: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override fun getTariff(): TariffModel? {
        TODO("Not yet implemented")
    }

}