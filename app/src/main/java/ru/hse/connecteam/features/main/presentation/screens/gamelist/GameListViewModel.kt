package ru.hse.connecteam.features.main.presentation.screens.gamelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.main.data.GameStaticRepositoryImpl
import ru.hse.connecteam.features.main.domain.FiltersDomainModel
import ru.hse.connecteam.features.main.presentation.screens.filters.FiltersViewModel
import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.shared.utils.GAMES_CHUNK_LIMIT
import ru.hse.connecteam.shared.utils.paging.PaginationState

//@HiltViewModel@Inject
class GameListViewModel constructor(
    private val repository: GameStaticRepositoryImpl
) : ViewModel() {
    private val limit = GAMES_CHUNK_LIMIT
    var tabIndex: Int by mutableStateOf(0)
        private set
    var hasTariff: Boolean by mutableStateOf(false)
        private set
    var filtersOpen: Boolean by mutableStateOf(false)
        private set

    var currentFilters by mutableStateOf(FiltersDomainModel())
        private set

    fun openFilters() {
        filtersOpen = true
    }

    var filtersViewModel by mutableStateOf(
        FiltersViewModel(
            initState = currentFilters,
            repository = GameStaticRepositoryImpl()
        )
    )

    fun hideFilters() {
        filtersOpen = false
    }

    fun applyFilters(filters: FiltersDomainModel) {
        currentFilters = filters
        filtersViewModel.clearList()
        filtersViewModel =  FiltersViewModel(
            initState = filters,
            repository = GameStaticRepositoryImpl()
        )
        resetLists()
        getMyGames()
        getParticipatedGames()
    }

    private var initialized by mutableStateOf(false)

    val myGamesList = mutableStateListOf<SimpleGame>()
    val participatedGamesList = mutableStateListOf<SimpleGame>()

    private var offsetMyGames by mutableStateOf(1)
    private var offsetParticipatedGames by mutableStateOf(1)

    var canPaginateMyGames by mutableStateOf(false)
    var canPaginateParticipatedGames by mutableStateOf(false)

    var listStateMyGames by mutableStateOf(PaginationState.IDLE)
    var listStateParticipatedGame by mutableStateOf(PaginationState.IDLE)

    fun getMyGames() {
        if (offsetMyGames == 1 ||
            (offsetMyGames != 1 && canPaginateMyGames) &&
            listStateMyGames == PaginationState.IDLE
        ) {
            listStateMyGames =
                if (offsetMyGames == 1) PaginationState.LOADING
                else PaginationState.PAGINATING
            val offsetIO = offsetMyGames
            CoroutineScope(Dispatchers.IO).launch {
                val gamesList = repository.loadMyGamesChunk(offsetIO)
                withContext(Dispatchers.Main) {
                    if (!gamesList.isNullOrEmpty()) {
                        canPaginateMyGames = gamesList.size == limit

                        if (offsetMyGames == 1) {
                            myGamesList.clear()
                            myGamesList.addAll(gamesList)
                        } else {
                            myGamesList.addAll(gamesList)
                        }

                        listStateMyGames = PaginationState.IDLE

                        if (canPaginateMyGames)
                            offsetMyGames += limit
                    } else {
                        listStateMyGames =
                            if (offsetMyGames == 1) PaginationState.ERROR
                            else PaginationState.PAGINATION_EXHAUST
                    }
                }
            }
        }
    }

    fun getParticipatedGames() {
        if (offsetParticipatedGames == 1 ||
            (offsetParticipatedGames != 1 && canPaginateParticipatedGames)
            && listStateParticipatedGame == PaginationState.IDLE
        ) {
            listStateParticipatedGame =
                if (offsetParticipatedGames == 1) PaginationState.LOADING
                else PaginationState.PAGINATING
            val offsetIO = offsetParticipatedGames
            CoroutineScope(Dispatchers.IO).launch {
                val gamesList = repository.loadParticipatedGamesChunk(offsetIO)
                withContext(Dispatchers.Main) {
                    if (!gamesList.isNullOrEmpty()) {
                        canPaginateParticipatedGames = gamesList.size == limit

                        if (offsetParticipatedGames == 1) {
                            participatedGamesList.clear()
                            participatedGamesList.addAll(gamesList)
                        } else {
                            participatedGamesList.addAll(gamesList)
                        }

                        listStateParticipatedGame = PaginationState.IDLE

                        if (canPaginateParticipatedGames)
                            offsetParticipatedGames += limit
                    } else {
                        listStateParticipatedGame =
                            if (offsetParticipatedGames == 1) PaginationState.ERROR
                            else PaginationState.PAGINATION_EXHAUST
                    }
                }
            }
        }
    }

    init {
        if (!initialized) {
            hasTariff = true
            getParticipatedGames()
            if (hasTariff) {
                tabIndex = 0
                getMyGames()
            } else {
                tabIndex = 1
            }
            initialized = true
        }
    }

    fun indexChanged(index: Int) {
        tabIndex = index
    }

    private fun resetLists() {
        offsetMyGames = 1
        offsetParticipatedGames = 1
        listStateMyGames = PaginationState.IDLE
        listStateParticipatedGame = PaginationState.IDLE
        canPaginateMyGames = false
        canPaginateParticipatedGames = false
    }

    override fun onCleared() {
        resetLists()
        initialized = false
        super.onCleared()
    }
}