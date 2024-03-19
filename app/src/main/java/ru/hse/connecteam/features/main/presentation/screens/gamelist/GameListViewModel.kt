package ru.hse.connecteam.features.main.presentation.screens.gamelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.main.domain.GameStaticRepository
import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.shared.utils.GAMES_CHUNK_LIMIT
import ru.hse.connecteam.shared.utils.paging.PaginationState
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val repository: GameStaticRepository
) : ViewModel() {
    private val limit = GAMES_CHUNK_LIMIT
    var tabIndex: Int by mutableStateOf(0)
        private set
    var hasTariff: Boolean by mutableStateOf(false)
        private set
    /*var filtersOpen: Boolean by mutableStateOf(false)
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
         filtersViewModel =  hiltViewModel()
         resetLists()
         getMyGames()
         getParticipatedGames()
     }*/

    private var initialized by mutableStateOf(false)

    val myGamesList = mutableStateListOf<SimpleGame>()
    val participatedGamesList = mutableStateListOf<SimpleGame>()

    private var offsetMyGames by mutableStateOf(0)
    private var offsetParticipatedGames by mutableStateOf(0)

    var canPaginateMyGames by mutableStateOf(false)
    var canPaginateParticipatedGames by mutableStateOf(false)

    var listStateMyGames by mutableStateOf(PaginationState.IDLE)
    var listStateParticipatedGame by mutableStateOf(PaginationState.IDLE)

    fun getMyGames() {
        if (offsetMyGames == 0 ||
            (offsetMyGames != 0 && canPaginateMyGames) &&
            listStateMyGames == PaginationState.IDLE
        ) {
            listStateMyGames =
                if (offsetMyGames == 0) PaginationState.LOADING
                else PaginationState.PAGINATING
            val offsetIO = offsetMyGames
            CoroutineScope(Dispatchers.IO).launch {
                val gamesList = repository.loadMyGamesChunk(offsetIO)
                withContext(Dispatchers.Main) {
                    if (!gamesList.isNullOrEmpty()) {
                        canPaginateMyGames = gamesList.size == limit

                        if (offsetMyGames == 0) {
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
                            if (offsetMyGames == 0) PaginationState.ERROR
                            else PaginationState.PAGINATION_EXHAUST
                    }
                }
            }
        }
    }

    fun getParticipatedGames() {
        if (offsetParticipatedGames == 0 ||
            (offsetParticipatedGames != 0 && canPaginateParticipatedGames)
            && listStateParticipatedGame == PaginationState.IDLE
        ) {
            listStateParticipatedGame =
                if (offsetParticipatedGames == 0) PaginationState.LOADING
                else PaginationState.PAGINATING
            val offsetIO = offsetParticipatedGames
            CoroutineScope(Dispatchers.IO).launch {
                val gamesList = repository.loadParticipatedGamesChunk(offsetIO)
                withContext(Dispatchers.Main) {
                    if (!gamesList.isNullOrEmpty()) {
                        canPaginateParticipatedGames = gamesList.size == limit

                        if (offsetParticipatedGames == 0) {
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
                            if (offsetParticipatedGames == 0) PaginationState.ERROR
                            else PaginationState.PAGINATION_EXHAUST
                    }
                }
            }
        }
    }

    init {
        if (!initialized) {
            viewModelScope.launch {
                repository.getTariffFlow().collectLatest { tariff ->
                    hasTariff = tariff != null
                    getParticipatedGames()
                    if (hasTariff) {
                        tabIndex = 0
                        getMyGames()
                    } else {
                        tabIndex = 1
                    }
                }
                initialized = true
            }
        }
    }

    fun indexChanged(index: Int) {
        tabIndex = index
    }

    private fun resetLists() {
        offsetMyGames = 0
        offsetParticipatedGames = 0
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

    fun onGameClick(){

    }
}