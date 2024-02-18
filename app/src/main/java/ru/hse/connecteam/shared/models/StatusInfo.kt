package ru.hse.connecteam.shared.models

enum class StatusInfo {
    OK,
    ERROR, // for any exception
    UNAUTHORISED, // for auth errors if need to specify
}