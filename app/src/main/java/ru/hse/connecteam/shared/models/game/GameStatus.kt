package ru.hse.connecteam.shared.models.game

enum class GameStatus {
    NOT_STARTED {
        override val desc: String = "не начата"
    },
    IN_PROCESS {
        override val desc: String = "в процессе"
    },
    FINISHED {
        override val desc: String = "завершена"
    };

    abstract val desc: String
}