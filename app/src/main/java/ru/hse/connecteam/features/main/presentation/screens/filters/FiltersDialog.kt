package ru.hse.connecteam.features.main.presentation.screens.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.features.main.domain.FiltersDomainModel
import ru.hse.connecteam.features.main.presentation.screens.filters.components.PlayersDropDown
import ru.hse.connecteam.features.main.presentation.screens.filters.components.RangeDatePicker
import ru.hse.connecteam.shared.utils.paging.PaginationState
import ru.hse.connecteam.ui.components.bars.TransparentAppBar
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.theme.FilledButtonLabel

@Composable
fun FiltersDialog(
    onPopBack: () -> Unit,
    onFiltersChosen: (FiltersDomainModel) -> Unit,
    viewModel: FiltersViewModel,
) {
    val lazyColumnListState = rememberLazyListState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate &&
                    (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ==
                            lazyColumnListState.layoutInfo.totalItemsCount - 1)
        }
    }
    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == PaginationState.IDLE)
            viewModel.getData()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TransparentAppBar(title = "Фильтры", onPopBack = { onPopBack() }) }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = "Игроки",
                style = FilledButtonLabel,
                modifier = Modifier.padding(top = 15.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
            )
            PlayersDropDown(
                items = viewModel.dataList,
                selectedItem = viewModel.selectedPlayerName,
                onItemSelected = { viewModel.onItemSelected(it) },
                paginationState = viewModel.listState,
                lazyColumnListState = lazyColumnListState,
            )
            Text(
                text = "Название игры",
                style = FilledButtonLabel,
                modifier = Modifier.padding(top = 15.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
            )
            BaseOutlinedTextInput(
                text = viewModel.gameTitle,
                onValueChanged = { viewModel.updateGameTitle(it) },
                label = "Поиск по названию игры",
                trailingIcon = {
                    GradientIcon(
                        image = Icons.Filled.Search,
                        description = "Поиск"
                    )
                })
            Text(
                text = "Дата",
                style = FilledButtonLabel,
                modifier = Modifier.padding(top = 15.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
            )
            RangeDatePicker(
                fromDate = viewModel.startDate,
                endDate = viewModel.endDate,
                fromDateChanged = { viewModel.fromDateChanged(it) },
                endDateChanged = { viewModel.endDateChanged(it) },
                isFromDateSelectable = { viewModel.isFromDateSelectable(it) },
                isEndDateSelectable = { viewModel.isEndDateSelectable(it) },
            )
            Spacer(modifier = Modifier.weight(1f))
            GradientFilledButton(
                text = "Применить фильтры",
                enabled = viewModel.filtersChangeEnabled,
                onClick = {
                    if (viewModel.filtersChangeEnabled) {
                        onFiltersChosen(viewModel.buildFiltersObject())
                        onPopBack()
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

/*
@Preview
@Composable
fun FiltersDialogPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        val viewModel by remember {
            mutableStateOf(
                FiltersViewModel(
                    initState = FiltersDomainModel(
                        SimplePlayer(
                            "player",
                            "playerson1"
                        ), gameTitle = "", startDate = null, endDate = null
                    ),
                    repository = GameStaticRepositoryImpl()
                )
            )
        }
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            FiltersDialog(
                onPopBack = { },
                onFiltersChosen = {},
                viewModel = viewModel
            )
        }
    }
}*/