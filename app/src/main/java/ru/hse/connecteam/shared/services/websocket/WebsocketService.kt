package ru.hse.connecteam.shared.services.websocket

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ru.hse.connecteam.shared.services.api.WS_URL
import javax.inject.Inject

class WebsocketService @Inject constructor() {
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private val _messages = MutableStateFlow<Pair<Boolean, String>?>(null)
    val messages = _messages.asStateFlow()

    private val okHttpClient = OkHttpClient()
    private var webSocket: WebSocket? = null

    private val webSocketListener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            _isConnected.value = true
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            _messages.update { Pair(false, text) }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            _isConnected.value = false
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
        }
    }

    fun connect(token: String) {
        val webSocketUrl = "$WS_URL?token=$token"

        val request = Request.Builder()
            .url(webSocketUrl)
            .build()

        webSocket = okHttpClient.newWebSocket(request, webSocketListener)
    }

    fun disconnect() {
        webSocket?.close(1000, "Disconnected by client")
    }

    fun shutdown() {
        okHttpClient.dispatcher().executorService().shutdown()
    }

    fun sendMessage(text: String): Boolean {
        return if (_isConnected.value) {
            if (webSocket?.send(text) == true) {
                _messages.update { Pair(true, text) }
                true
            } else {
                false
            }
        } else {
            false
        }
    }
}