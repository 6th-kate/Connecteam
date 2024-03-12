package ru.hse.connecteam.ui.components.bars

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DefaultWhite

@Composable
fun BottomNavBar() {
    Box(
        modifier = Modifier
            .height(118.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .height(75.dp)
                .aspectRatio(1f)
                .background(
                    brush = BaseGradientBrush, shape = CircleShape
                )
                .align(Alignment.TopCenter)
                .clickable {}, contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Создать игру",
                tint = DefaultWhite,
                modifier = Modifier.size(65.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 45.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            CurvyBar()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.SportsEsports,
                    contentDescription = "Игры",
                    tint = DefaultWhite,
                    modifier = Modifier.size(38.dp)
                )
                Icon(
                    imageVector = Icons.Outlined.PermIdentity,
                    contentDescription = "Профиль",
                    tint = DefaultWhite,
                    modifier = Modifier.size(38.dp)
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun BottomBarPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(bottomBar = { BottomNavBar() }) {}
        }
    }
}
