package com.bruno.lumen.feature.drawing.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.Stroke as DrawStroke
import com.bruno.lumen.domain.model.Stroke
import com.bruno.lumen.domain.vision.HandStatus
import com.bruno.lumen.feature.drawing.vm.DrawingViewModel
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.atan2

@Composable
fun DrawingCanvas(
    strokes: List<Stroke>,
    handStatus: HandStatus? = null,
    currentTool: DrawingViewModel.Tool = DrawingViewModel.Tool.BRUSH,
    currentWidth: Float = 10f,
    currentColor: Int = 0xFF00E5FF.toInt(),
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        strokes.forEach { stroke ->
            if (stroke.points.size < 1) return@forEach
            
            when (stroke.type) {
                "CUBE_3D", "CRYSTAL" -> draw3DShape(stroke, size.width, size.height)
                "LIGHTNING" -> drawLightning(stroke, size.width, size.height)
                "FIRE" -> drawFire(stroke, size.width, size.height)
                "GALAXY" -> drawGalaxy(stroke, size.width, size.height)
                "WEB" -> drawWeb(stroke, size.width, size.height)
                "HEART" -> drawShape(stroke, size.width, size.height, "HEART")
                "STAR" -> drawShape(stroke, size.width, size.height, "STAR")
                "TRIANGLE" -> drawShape(stroke, size.width, size.height, "TRIANGLE")
                "SQUARE" -> drawShape(stroke, size.width, size.height, "SQUARE")
                "CIRCLE" -> drawShape(stroke, size.width, size.height, "CIRCLE")
                "CALLIGRAPHY" -> drawCalligraphy(stroke, size.width, size.height)
                "AIRBRUSH", "SPRAY" -> drawAirbrush(stroke, size.width, size.height)
                "RAINBOW" -> drawRainbow(stroke, size.width, size.height)
                "SHIMMER" -> drawShimmer(stroke, size.width, size.height)
                "PARTICLES" -> drawParticles(stroke, size.width, size.height)
                "RIBBON" -> drawRibbon(stroke, size.width, size.height)
                "NEON_PULSE" -> drawNeonPulse(stroke, size.width, size.height)
                else -> drawNeonStroke(stroke, size.width, size.height)
            }
        }

        // Draw Tool Preview
        val firstHand = handStatus?.hands?.firstOrNull()
        if (firstHand?.indexTip != null && currentTool != DrawingViewModel.Tool.ERASER) {
            val tip = firstHand.indexTip!!
            val x = tip.x * size.width
            val y = tip.y * size.height
            drawCircle(
                color = Color(currentColor).copy(alpha = 0.4f),
                radius = currentWidth * 1.5f,
                center = Offset(x, y),
                style = DrawStroke(width = 2.dp.toPx())
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawFire(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    stroke.points.forEach { p ->
        val x = p.x * w
        val y = p.y * h
        val color = Color(stroke.color)
        for (i in 0 until 3) {
            val ox = (Math.random().toFloat() - 0.5f) * 20f
            val oy = -(Math.random().toFloat()) * 30f
            drawCircle(color.copy(alpha = 0.3f), radius = stroke.width * (1f + i), center = Offset(x + ox, y + oy))
        }
        drawCircle(Color.White.copy(alpha = 0.6f), radius = stroke.width * 0.5f, center = Offset(x, y))
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawRainbow(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    for (i in 0 until stroke.points.size - 1) {
        val p1 = stroke.points[i]
        val p2 = stroke.points[i+1]
        val hue = (System.currentTimeMillis() / 10 + i * 5) % 360
        drawLine(
            color = Color.hsv(hue.toFloat(), 1f, 1f),
            start = Offset(p1.x * w, p1.y * h),
            end = Offset(p2.x * w, p2.y * h),
            strokeWidth = stroke.width,
            cap = StrokeCap.Round
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawGalaxy(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    stroke.points.forEach { p ->
        val x = p.x * w
        val y = p.y * h
        val color = Color(stroke.color)
        repeat(5) {
            val angle = Math.random() * Math.PI * 2
            val dist = Math.random() * stroke.width * 4f
            val px = x + (cos(angle) * dist).toFloat()
            val py = y + (sin(angle) * dist).toFloat()
            drawCircle(color.copy(alpha = 0.5f), radius = 2f, center = Offset(px, py))
        }
        drawCircle(color.copy(alpha = 0.1f), radius = stroke.width * 3f, center = Offset(x, y))
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawWeb(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    val color = Color(stroke.color).copy(alpha = 0.4f)
    for (i in stroke.points.indices) {
        val p1 = stroke.points[i]
        val x1 = p1.x * w
        val y1 = p1.y * h
        for (j in (i - 8).coerceAtLeast(0) until i) {
            val p2 = stroke.points[j]
            drawLine(color, Offset(x1, y1), Offset(p2.x * w, p2.y * h), strokeWidth = 1f)
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawShimmer(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    stroke.points.forEach { p ->
        repeat(3) {
            val ox = (Math.random().toFloat() - 0.5f) * 40f
            val oy = (Math.random().toFloat() - 0.5f) * 40f
            drawCircle(Color.White, radius = 2f, center = Offset(p.x * w + ox, p.y * h + oy))
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawParticles(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    stroke.points.lastOrNull()?.let { p ->
        repeat(5) {
            val angle = Math.random() * Math.PI * 2
            val dist = Math.random() * 50f
            val px = p.x * w + (cos(angle) * dist).toFloat()
            val py = p.y * h + (sin(angle) * dist).toFloat()
            drawCircle(Color.hsv((Math.random() * 360).toFloat(), 0.8f, 1f), radius = 4f, center = Offset(px, py))
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawRibbon(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    if (stroke.points.size < 3) return
    val color = Color(stroke.color)
    val path = Path()
    for (i in 2 until stroke.points.size) {
        val p = stroke.points[i]
        val prev = stroke.points[i-2]
        val angle = atan2(p.y - prev.y, p.x - prev.x)
        val ox = cos(angle + Math.PI/2).toFloat() * stroke.width
        val oy = sin(angle + Math.PI/2).toFloat() * stroke.width
        drawLine(color, Offset(p.x * w + ox, p.y * h + oy), Offset(prev.x * w + ox, prev.y * h + oy), strokeWidth = 2f)
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawNeonPulse(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    val pulse = sin(System.currentTimeMillis() / 200f) * 5f
    val path = Path().apply {
        stroke.points.forEachIndexed { index, point ->
            val x = point.x * w
            val y = point.y * h
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
    }
    drawPath(path, Color(stroke.color).copy(alpha = 0.3f), style = DrawStroke(width = (stroke.width + pulse) * 3f, cap = StrokeCap.Round))
    drawPath(path, Color(stroke.color), style = DrawStroke(width = stroke.width + pulse, cap = StrokeCap.Round))
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCalligraphy(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    val path = Path().apply {
        stroke.points.forEachIndexed { index, point ->
            val x = point.x * w
            val y = point.y * h
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
    }
    drawPath(path, Color(stroke.color), style = DrawStroke(width = stroke.width * 1.5f, cap = StrokeCap.Square))
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawAirbrush(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    stroke.points.forEach { p ->
        repeat(12) {
            val ox = (Math.random().toFloat() - 0.5f) * stroke.width * 5f
            val oy = (Math.random().toFloat() - 0.5f) * stroke.width * 5f
            drawCircle(Color(stroke.color).copy(alpha = 0.2f), radius = 2f, center = Offset(p.x * w + ox, p.y * h + oy))
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawShape(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float, shape: String) {
    val lastPoint = stroke.points.last()
    val x = lastPoint.x * w
    val y = lastPoint.y * h
    val size = stroke.width * 5f
    val color = Color(stroke.color)
    
    when (shape) {
        "HEART" -> {
            val path = Path().apply {
                moveTo(x, y + size * 0.5f)
                cubicTo(x - size, y - size, x - size * 1.5f, y + size * 1.5f, x, y + size * 2f)
                cubicTo(x + size * 1.5f, y + size * 1.5f, x + size, y - size, x, y + size * 0.5f)
            }
            drawPath(path, color)
        }
        "STAR" -> {
            val path = Path().apply {
                for (i in 0 until 10) {
                    val r = if (i % 2 == 0) size else size * 0.5f
                    val angle = Math.PI * i / 5 - Math.PI / 2
                    val px = (x + cos(angle) * r).toFloat()
                    val py = (y + sin(angle) * r).toFloat()
                    if (i == 0) moveTo(px, py) else lineTo(px, py)
                }
                close()
            }
            drawPath(path, color)
        }
        "TRIANGLE" -> {
            val path = Path().apply {
                moveTo(x, y - size)
                lineTo(x - size, y + size)
                lineTo(x + size, y + size)
                close()
            }
            drawPath(path, color)
        }
        "SQUARE" -> {
            drawRect(color, Offset(x - size, y - size), androidx.compose.ui.geometry.Size(size * 2, size * 2))
        }
        "CIRCLE" -> {
            drawCircle(color, radius = size, center = Offset(x, y))
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawNeonStroke(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    val path = Path().apply {
        stroke.points.forEachIndexed { index, point ->
            val x = point.x * w
            val y = point.y * h
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
    }
    val baseColor = Color(stroke.color)
    drawPath(path, baseColor.copy(alpha = 0.1f), style = DrawStroke(width = stroke.width * 5f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    drawPath(path, baseColor.copy(alpha = 0.4f), style = DrawStroke(width = stroke.width * 2f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    drawPath(path, Color.White, style = DrawStroke(width = stroke.width * 0.2f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    drawPath(path, baseColor, style = DrawStroke(width = stroke.width, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.draw3DShape(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    val lastPoint = stroke.points.last()
    val x = lastPoint.x * w
    val y = lastPoint.y * h
    val color = Color(stroke.color)
    val time = System.currentTimeMillis() / 1000f
    
    val size = stroke.width * 4f
    val vertices = if (stroke.type == "CUBE_3D") {
        listOf(
            floatArrayOf(-1f,-1f,-1f), floatArrayOf(1f,-1f,-1f), floatArrayOf(1f,1f,-1f), floatArrayOf(-1f,1f,-1f),
            floatArrayOf(-1f,-1f,1f), floatArrayOf(1f,-1f,1f), floatArrayOf(1f,1f,1f), floatArrayOf(-1f,1f,1f)
        )
    } else {
        listOf(
            floatArrayOf(0f,-1.5f,0f), floatArrayOf(1f,0f,1f), floatArrayOf(1f,0f,-1f),
            floatArrayOf(-1f,0f,-1f), floatArrayOf(-1f,0f,1f), floatArrayOf(0f,1.5f,0f)
        )
    }

    val projected = vertices.map { v ->
        val rotX = time + (x / 500f)
        val rotY = time + (y / 500f)
        
        var vy = v[1] * cos(rotX) - v[2] * sin(rotX)
        var vz = v[1] * sin(rotX) + v[2] * cos(rotX)
        
        var vx = v[0] * cos(rotY) - vz * sin(rotY)
        vz = v[0] * sin(rotY) + vz * cos(rotY)
        
        val p = 4f / (4f + vz)
        Offset(x + vx * size * p, y + vy * size * p)
    }

    if (stroke.type == "CUBE_3D") {
        val connections = listOf(0 to 1, 1 to 2, 2 to 3, 3 to 0, 4 to 5, 5 to 6, 6 to 7, 7 to 4, 0 to 4, 1 to 5, 2 to 6, 3 to 7)
        connections.forEach { (a, b) ->
            drawLine(color, projected[a], projected[b], strokeWidth = 3f, cap = StrokeCap.Round)
        }
    } else {
        val connections = listOf(0 to 1, 0 to 2, 0 to 3, 0 to 4, 5 to 1, 5 to 2, 5 to 3, 5 to 4, 1 to 2, 2 to 3, 3 to 4, 4 to 1)
        connections.forEach { (a, b) ->
            drawLine(color, projected[a], projected[b], strokeWidth = 3f, cap = StrokeCap.Round)
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawLightning(stroke: com.bruno.lumen.domain.model.Stroke, w: Float, h: Float) {
    if (stroke.points.size < 2) return
    val color = Color(stroke.color)
    for (i in 0 until stroke.points.size - 1) {
        val p1 = stroke.points[i]
        val p2 = stroke.points[i+1]
        var curX = p1.x * w
        var curY = p1.y * h
        val targetX = p2.x * w
        val targetY = p2.y * h
        
        repeat(4) {
            val nextX = curX + (targetX - curX) * 0.25f + (Math.random().toFloat() - 0.5f) * 30f
            val nextY = curY + (targetY - curY) * 0.25f + (Math.random().toFloat() - 0.5f) * 30f
            drawLine(color, Offset(curX, curY), Offset(nextX, nextY), strokeWidth = 4f, cap = StrokeCap.Round)
            drawLine(Color.White, Offset(curX, curY), Offset(nextX, nextY), strokeWidth = 1.5f, cap = StrokeCap.Round)
            curX = nextX
            curY = nextY
        }
    }
}
