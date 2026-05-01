package com.candy.tasbeeh.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.candy.tasbeeh.model.Tile
import kotlin.math.min

@Composable
fun BoardCanvas(
    board: Array<Array<Tile>>,
    rows: Int,
    cols: Int,
    tileSize: Float = 60f,
    particles: List<com.candy.tasbeeh.model.Particle>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = rows * tileSize

        // رسم الخلفية الشفافة
        drawRoundRect(
            color = Color.White.copy(alpha = 0.05f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(32f, 32f),
            size = Size(cols * tileSize + 16, rows * tileSize + 16),
            topLeft = Offset(8f, 8f)
        )

        // رسم الأحجار
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val tile = board[r][c]
                if (tile.opacity <= 0.1f) continue

                val x = c * tileSize + tile.offsetX + 16
                val y = r * tileSize + tile.offsetY + 16

                drawTile(
                    tile = tile,
                    x = x,
                    y = y,
                    size = tileSize,
                    textMeasurer = textMeasurer
                )
            }
        }

        // رسم الجسيمات (الورد)
        for (p in particles) {
            if (p.life <= 0f) continue
            drawParticle(p)
        }
    }
}

fun DrawScope.drawTile(
    tile: Tile,
    x: Float,
    y: Float,
    size: Float,
    textMeasurer: TextMeasurer
) {
    val tileColor = Color(tile.color.hex)
    val radius = 12f

    // الظل
    drawRoundRect(
        color = Color.Black.copy(alpha = 0.3f),
        topLeft = Offset(x + 4, y + 4),
        size = Size(size - 8, size - 8),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(radius, radius)
    )

    // التدرج اللوني للحجر (تأثير زجاجي)
    val gradient = Brush.radialGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.4f),
            tileColor
        ),
        center = Offset(x + size * 0.3f, y + size * 0.3f),
        radius = size
    )

    drawRoundRect(
        brush = gradient,
        topLeft = Offset(x, y),
        size = Size(size - 8, size - 8),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(radius, radius)
    )

    // النص
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(tile.text),
        style = TextStyle(
            color = Color.White,
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.5f),
                blurRadius = 4f
            )
        )
    )

    val textX = x + (size - 8) / 2 - textLayoutResult.size.width / 2
    val textY = y + (size - 8) / 2 - textLayoutResult.size.height / 2

    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(textX, textY)
    )
}

fun DrawScope.drawParticle(p: com.candy.tasbeeh.model.Particle) {
    val alpha = if (p.life > 0.4f) 1f else p.life / 0.4f

    drawContext.canvas.nativeCanvas.apply {
        val paint = android.graphics.Paint().apply {
            this.alpha = (alpha * 255).toInt()
            textSize = p.size
            textAlign = android.graphics.Paint.Align.CENTER
        }
        drawText(p.char, p.x, p.y, paint)
    }
}
