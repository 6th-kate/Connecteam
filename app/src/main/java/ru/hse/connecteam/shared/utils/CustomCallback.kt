package ru.hse.connecteam.shared.utils

interface CustomCallback<T> {
    fun onSuccess(value: T?)
    fun onFailure()
}

interface CustomVoidCallback {
    fun onSuccess()
    fun onFailure()
}