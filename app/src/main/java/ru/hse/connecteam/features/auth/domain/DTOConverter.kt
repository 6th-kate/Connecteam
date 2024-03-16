package ru.hse.connecteam.features.auth.domain

import ru.hse.connecteam.shared.services.api.GameCreated
import ru.hse.connecteam.shared.services.api.UserByIdData

class DTOConverter {
    companion object {
        fun convert(game: GameCreated): GameDomainModel {
            return GameDomainModel(name = game.name)
        }

        fun convert(owner: UserByIdData): TariffDomainModel {
            return TariffDomainModel(ownerFullName = "${owner.first_name} ${owner.second_name}")
        }
    }
}