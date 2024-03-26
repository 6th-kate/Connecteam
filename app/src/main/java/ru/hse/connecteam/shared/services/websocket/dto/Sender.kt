package ru.hse.connecteam.shared.services.websocket.dto

import kotlinx.serialization.Serializable

@Serializable
data class Sender(val id: Int, val name: String? = null)
