package ru.hse.connecteam.features.main.presentation.screens.gamelist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.shared.models.game.SimpleGame
import ru.hse.connecteam.shared.utils.paging.PaginationState
import ru.hse.connecteam.ui.components.animated.LoadingAnimation

@Composable
fun GameList(
    items: List<SimpleGame>,
    paginationState: PaginationState,
    canPaginate: Boolean,
    addData: () -> Unit,
    onGameClick: (SimpleGame) -> Unit,
) {
    val lazyColumnListState = rememberLazyListState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            canPaginate &&
                    ((lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1) >=
                            lazyColumnListState.layoutInfo.totalItemsCount - 2)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && paginationState == PaginationState.IDLE)
            addData()
    }

    LazyColumn(
        state = lazyColumnListState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = items) { item ->
            GameItem(game = item, onClick = { onGameClick(item) })
        }
        item(
            key = paginationState,
        ) {
            if (paginationState == PaginationState.LOADING ||
                paginationState == PaginationState.PAGINATING
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        LoadingAnimation(indicatorSize = 20.dp)
                    }
                }
            }
        }
    }
}