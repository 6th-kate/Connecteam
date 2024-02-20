package ru.hse.connecteam.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val TextInputStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    brush = BaseGradientBrush,
)

val FilledButtonLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = DefaultWhite,
)

val OutlinedButtonLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 17.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    brush = BaseGradientBrush,
)

val BigWhiteLabel37 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 37.sp,
    lineHeight = 37.sp,
    letterSpacing = 0.5.sp,
    color = DefaultWhite,
)

val BigGradientLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 37.sp,
    lineHeight = 37.sp,
    letterSpacing = 0.5.sp,
    brush = BaseGradientBrush,
)

val DisabledButtonLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 17.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = WhiteSemiTransparent25,
)

val SpanButtonWhiteLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 17.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = DefaultWhite,
)

val BigWhiteLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 23.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.5.sp,
    color = DefaultWhite,
)

val MediumWhiteLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.5.sp,
    color = DefaultWhite,
)

val DefaultRedLabel17 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 17.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = DefaultRed,
)

val SpanButtonGrayLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    textDecoration = TextDecoration.Underline,
    fontSize = 17.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = WhiteSemiTransparent25,
)

val SmallGrayLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = WhiteSemiTransparent25,
)

val ClickableTextGrayLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.5.sp,
    textDecoration = TextDecoration.Underline,
    color = WhiteSemiTransparent25,
)

val ErrorInputLabel = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.5.sp,
    color = ErrorRed,
)

val OTPInput = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 23.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = DefaultWhite,
)