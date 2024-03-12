package ru.hse.connecteam.features.main.presentation.screens.gamelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.hse.connecteam.features.main.data.GameStaticRepositoryImpl
import ru.hse.connecteam.features.main.presentation.screens.filters.FiltersDialog
import ru.hse.connecteam.features.main.presentation.screens.gamelist.components.GameList
import ru.hse.connecteam.features.main.presentation.screens.gamelist.components.GamesTabRow
import ru.hse.connecteam.features.tariffs.presentation.components.PayWall
import ru.hse.connecteam.ui.components.bars.TransparentAppBar
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun GameListScreen(
    navController: NavController,
    viewModel: GameListViewModel
) {
    Scaffold(topBar = { TransparentAppBar(title = "") }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            GamesTabRow(
                onFiltersOpen = { viewModel.openFilters() },
                selectedTabIndex = viewModel.tabIndex,
                moveToTab = { viewModel.indexChanged(it) }
            )
            when (viewModel.tabIndex) {
                0 -> if (viewModel.hasTariff) {
                    GameList(
                        items = viewModel.myGamesList,
                        paginationState = viewModel.listStateMyGames,
                        canPaginate = viewModel.canPaginateMyGames,
                        addData = { viewModel.getMyGames() }
                    )
                } else PayWall(
                    navController = navController,
                    message = "Эта функция доступна только пользователям с активным тарифом"
                )

                1 -> GameList(
                    items = viewModel.participatedGamesList,
                    paginationState = viewModel.listStateParticipatedGame,
                    canPaginate = viewModel.canPaginateParticipatedGames,
                    addData = { viewModel.getParticipatedGames() }
                )
            }
        }
    }
    if (viewModel.filtersOpen) {
        FiltersDialog(
            onPopBack = { viewModel.hideFilters() },
            onFiltersChosen = { viewModel.applyFilters(it) },
            viewModel = viewModel.filtersViewModel
        )
    }
}

@Preview
@Composable
fun GameListPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        val viewModel by remember {
            mutableStateOf(GameListViewModel(repository = GameStaticRepositoryImpl()))
        }
        val navController = rememberNavController()
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            GameListScreen(navController, viewModel)
        }
    }
}