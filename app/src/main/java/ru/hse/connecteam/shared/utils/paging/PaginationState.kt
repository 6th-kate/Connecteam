package ru.hse.connecteam.shared.utils.paging

enum class PaginationState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
}