package ru.hse.connecteam.features.profile.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.DefaultWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentAppBar(title: String) {
    TopAppBar(
        modifier = Modifier.padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 0.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = DefaultWhite,
            titleContentColor = DefaultWhite,
        ),
        title = {
            Text(title, style = BigWhiteLabel)
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = "Назад"
            )
        }
    )
}