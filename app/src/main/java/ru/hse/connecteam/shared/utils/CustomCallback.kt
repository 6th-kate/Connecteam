package ru.hse.connecteam.shared.utils

interface CustomCallback<T> {
    fun onSuccess(value: T?)
    fun onFailure()
}