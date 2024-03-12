package ru.hse.connecteam.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val BaseGradientBrush = Brush.horizontalGradient(
    listOf(
        LightBlueGradient,
        LightGreenGradient,
    )
)

val BaseGrayBrush = Brush.horizontalGradient(
    listOf(
        WhiteSemiTransparent25,
        WhiteSemiTransparent25,
    )
)

val BaseSweepGradientBrush = Brush.sweepGradient(
    listOf(
        LightGreenGradient,
        Color(0xFF31F0C4),
        Color(0xFF38E7CE),
        Color(0xFF40DFD9),
        Color(0xFF47D7E3),
        Color(0xFF05A2E2),
        Color(0xFF0074F8),
        Color(0xFF05A2E2),
        Color(0xFF17DDDA),
        LightBlueGradient,
    )
)