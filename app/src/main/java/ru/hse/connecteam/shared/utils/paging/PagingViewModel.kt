package ru.hse.connecteam.shared.utils.paging

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

abstract class PagingViewModel<T> : ViewModel() {
    val dataList = mutableStateListOf<T>()

    protected var offset by mutableStateOf(1)
    var canPaginate by mutableStateOf(false)
    protected var initialized by mutableStateOf(false)
    var listState by mutableStateOf(PaginationState.IDLE)

    abstract fun getData()

    fun clearList() {
        dataList.clear()
    }

    override fun onCleared() {
        offset = 1
        listState = PaginationState.IDLE
        canPaginate = false
        initialized = false
        super.onCleared()
    }
}