package ru.hse.connecteam.ui.components.bars

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.DefaultBlack

fun getPath(size: Size, filled: Boolean = false): Path {
    val width = size.width
    val height = size.height
    val halfWidth = width.times(.5f)
    val halfHeight = height.times(.7f)
    val radiusFloat = width.times(.03f)

    val startPoints = listOf(
        PointF(0f, radiusFloat),

        PointF(width, radiusFloat),
    )

    val path = Path().apply {
        startPoints.forEach { point ->
            val curveXPart1 = if (point.x > halfWidth) width.minus(radiusFloat)
            else radiusFloat
            val lineEndX =
                if (point.x > halfWidth) width.minus(width.times(.29f)).minus(radiusFloat)
                else width.times(.29f).plus(radiusFloat)
            val curveXPart2End = if (point.x > halfWidth) lineEndX.minus(radiusFloat.times(.7f))
            else lineEndX.plus(radiusFloat.times(.7f))
            val curveYPart2End = radiusFloat.times(.5f)
            val curve1 = PointF(curveXPart1, 0f)
            val curve2 = PointF(curveXPart2End, curveYPart2End)
            val curveXPart3End = if (point.x > halfWidth) curve2.x.minus(radiusFloat.times(1.5f))
            else curve2.x.plus(radiusFloat.times(1.5f))
            val curveXPart2Pivot = if (point.x > halfWidth) curve2.x.plus(radiusFloat.times(.3f))
            else curve2.x.minus(radiusFloat.times(.3f))

            if (!filled) {
                moveTo(point.x, point.y)
            } else {
                moveTo(point.x, height)
                lineTo(point.x, point.y)
            }
            quadraticBezierTo(
                point.x,
                0f,
                curve1.x,
                curve1.y,
            )
            lineTo(lineEndX, 0f)
            quadraticBezierTo(
                curveXPart2Pivot,
                0f,
                curve2.x,
                curve2.y,
            )
            quadraticBezierTo(
                curveXPart3End,
                halfHeight,
                halfWidth,
                halfHeight,
            )
            if (filled) {
                lineTo(halfWidth, height)
            }
        }
    }
    return path
}

@Composable
fun CurvyBar() {
    Canvas(
        modifier = Modifier
            .height(85.dp)
            .fillMaxWidth()
    ) {
        drawPath(
            path = getPath(size),
            brush = BaseGradientBrush,
            style = Stroke(width = 6f, cap = StrokeCap.Round)
        )
        drawPath(
            path = getPath(size, filled = true), color = DefaultBlack, style = Fill
        )
    }
}