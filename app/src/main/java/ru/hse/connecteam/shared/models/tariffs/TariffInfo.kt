package ru.hse.connecteam.shared.models.tariffs

enum class TariffInfo {
    SIMPLE {
        override val possibilities: String =
            "- Возможность создания игры\n" +
                    "- Наличие трех тем с вопросами\n" +
                    "- Не более 10 вопросов на каждую тему\n" +
                    "- Возможность добавлять в одну игру не более 4 игроков\n"
        override val tariffName: String = "Простой"
        override val standardCost: Int = 399
    },
    ADVANCED {
        override val possibilities: String =
            "- Возможность создания игры\n" +
                    "- 5 тем с вопросами\n" +
                    "- до 20 вопросов на каждую тему\n" +
                    "- Возможность приглашать в одну игру до 5 игроков\n" +
                    "- Возможность выбора тем для конкретной игры\n"
        override val tariffName: String = "Расширенный"
        override val standardCost: Int = 599
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
        override val standardCost: Int = 899
    };

    abstract val possibilities: String
    abstract val tariffName: String
    abstract val standardCost: Int
}