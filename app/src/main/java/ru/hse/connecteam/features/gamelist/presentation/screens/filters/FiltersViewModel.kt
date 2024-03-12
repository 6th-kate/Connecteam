package ru.hse.connecteam.features.gamelist.presentation.screens.filters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.gamelist.domain.FiltersDomainModel
import ru.hse.connecteam.features.gamelist.domain.GameStaticRepository
import ru.hse.connecteam.shared.models.game.SimplePlayer
import ru.hse.connecteam.shared.utils.PLAYERS_CHUNK_LIMIT
import ru.hse.connecteam.shared.utils.getDate
import ru.hse.connecteam.shared.utils.paging.PaginationState
import ru.hse.connecteam.shared.utils.paging.PagingViewModel

class FiltersViewModel(
    initState: FiltersDomainModel,
    private val repository: GameStaticRepository
) : PagingViewModel<SimplePlayer>() {

    private val limit: Int = PLAYERS_CHUNK_LIMIT
    private var startDateLong: Long? by mutableStateOf(initState.startDate)
    private var endDateLong: Long? by mutableStateOf(initState.endDate)

    var startDate by mutableStateOf(
        if (initState.startDate != null) getDate(
            initState.startDate,
            "dd.MM.yyyy"
        ) else ""
    )
        private set

    var endDate by mutableStateOf(
        if (initState.endDate != null) getDate(
            initState.endDate,
            "dd.MM.yyyy"
        ) else ""
    )
        private set

    var gameTitle by mutableStateOf(initState.gameTitle ?: "")
        private set
    var filtersChangeEnabled by mutableStateOf(false)
        private set

    fun updateGameTitle(value: String) {
        gameTitle = value
        filtersChangeEnabled = true
    }

    fun fromDateChanged(value: Long?) {
        startDate = if (value == null) "" else getDate(value, "dd.MM.yyyy")
        startDateLong = value
        filtersChangeEnabled = true
    }

    fun endDateChanged(value: Long?) {
        endDate = if (value == null) "" else getDate(value, "dd.MM.yyyy")
        endDateLong = value
        filtersChangeEnabled = true
    }

    fun isFromDateSelectable(value: Long): Boolean {
        return if (endDateLong != null) {
            (value <= (System.currentTimeMillis())) &&
                    (value < endDateLong!!)
        } else {
            value <= (System.currentTimeMillis())
        }
    }

    fun isEndDateSelectable(value: Long): Boolean {
        return if (startDateLong != null) {
            (value <= (System.currentTimeMillis())) &&
                    (value > startDateLong!!)
        } else {
            value <= (System.currentTimeMillis())
        }
    }

    private var selectedPlayer by mutableStateOf(initState.player)

    var selectedPlayerName by mutableStateOf(
        if (initState.player != null)
            "${initState.player.name} ${initState.player.surname}" else ""
    )

    fun onItemSelected(value: SimplePlayer) {
        selectedPlayer = value
        selectedPlayerName = "${value.name} ${value.surname}"
        filtersChangeEnabled = true
    }

    fun buildFiltersObject(): FiltersDomainModel {
        return FiltersDomainModel(selectedPlayer, gameTitle, startDateLong, endDateLong)
    }

    override fun getData() {
        if (offset == 1 || (offset != 1 && canPaginate) && listState == PaginationState.IDLE) {
            listState = if (offset == 1) PaginationState.LOADING else PaginationState.PAGINATING
            val offsetIO = offset
            CoroutineScope(Dispatchers.IO).launch {
                val playersList = repository.loadPlayersChunk(offsetIO)
                withContext(Dispatchers.Main) {
                    if (!playersList.isNullOrEmpty()) {
                        canPaginate = playersList.size == limit

                        if (offset == 1) {
                            dataList.clear()
                            dataList.addAll(playersList)
                        } else {
                            dataList.addAll(playersList)
                        }

                        listState = PaginationState.IDLE

                        if (canPaginate)
                            offset += limit
                    } else {
                        listState =
                            if (offset == 1) PaginationState.ERROR
                            else PaginationState.PAGINATION_EXHAUST
                    }
                }
            }
        }
    }

    init {
        if (!initialized) {
            getData()
            initialized = true
        }
    }
}