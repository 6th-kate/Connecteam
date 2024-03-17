package ru.hse.connecteam.features.main.domain

import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import androidx.core.os.ConfigurationCompat
import ru.hse.connecteam.shared.models.game.GameStatus
import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.shared.models.user.UserModel
import ru.hse.connecteam.shared.services.api.GameBasic
import ru.hse.connecteam.shared.services.api.GameCreated
import ru.hse.connecteam.shared.services.api.UserByIdData
import ru.hse.connecteam.shared.utils.STANDARD_BACKEND_DATE
import java.text.ParseException
import java.util.Date

class DTOConverter {
    companion object {
        fun convert(game: GameCreated): GameDomainModel {
            return GameDomainModel(name = game.name)
        }

        fun convert(game: GameBasic): SimpleGame {
            return SimpleGame(
                game.id,
                game.name,
                toGameStatus(game.status),
                toDateTime(game.start_date)
            )
        }

        fun convertForCreator(game: GameCreated): GameCreatedDomainModel {
            return GameCreatedDomainModel(id = game.id, invitationCode = game.invitation_code)
        }

        fun convert(owner: UserByIdData): TariffDomainModel {
            return TariffDomainModel(ownerFullName = "${owner.first_name} ${owner.second_name}")
        }

        fun convert(user: UserModel?): UserDomainModel? {
            return if (user == null) {
                null
            } else {
                UserDomainModel(user.firstName, user.surname, user.id, user.email)
            }
        }

        private fun toGameStatus(gameStatus: String?): GameStatus? {
            return when (gameStatus) {
                "not_started" -> GameStatus.NOT_STARTED
                "in_progress" -> GameStatus.IN_PROCESS
                "ended" -> GameStatus.FINISHED
                else -> null
            }
        }

        private fun toDateTime(time: String): Date? {
            return try {
                val currentLocale =
                    ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
                SimpleDateFormat(STANDARD_BACKEND_DATE, currentLocale).parse(time)
            } catch (e: ParseException) {
                null
            }
        }
    }
}