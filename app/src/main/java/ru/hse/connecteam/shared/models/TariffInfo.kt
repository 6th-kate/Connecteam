package ru.hse.connecteam.shared.models

enum class TariffInfo {
    SIMPLE {
        override val possibilities: String =
            "- Возможность создания игры\n" +
                    "- Наличие трех тем с вопросами\n" +
                    "- Не более 10 вопросов на каждую тему\n" +
                    "- Возможность добавлять в одну игру не более 4 игроков\n"
        override val tariffName: String = "Простой"
    },
    ADVANCED {
        override val possibilities: String =
            "- Возможность создания игры\n" +
                    "- 5 тем с вопросами\n" +
                    "- до 20 вопросов на каждую тему\n" +
                    "- Возможность приглашать в одну игру до 5 игроков\n" +
                    "- Возможность выбора тем для конкретной игры"
        override val tariffName: String = "Расширенный"
    },
    WIDE {
        override val possibilities: String =
            "- Возможность создания игры\n" +
                    "- Возможность добавления авторизованных пользователей" +
                    " сервиса в качестве дополнительных организаторов игр (до 3-х) человек." +
                    " Пользователь-владелец, купивший пакет, может управлять списком организаторов\n" +
                    "- 10 тем с вопросами\n" +
                    "- До 50 вопросов на каждую тему\n" +
                    "- Возможность выбора тем для конкретной игры\n"
        override val tariffName: String = "Широкий"
    };

    abstract val possibilities: String
    abstract val tariffName: String
}