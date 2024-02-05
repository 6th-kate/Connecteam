package ru.hse.connecteam.shared.utils

val PASSWORD_REGEX: Regex = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")
val EMAIL_REGEX: Regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
val NAME_REGEX: Regex = Regex("^[\\-'a-zA-ZА-Яа-я()]/u")