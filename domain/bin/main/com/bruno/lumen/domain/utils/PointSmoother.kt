package com.bruno.lumen.domain.utils

import com.bruno.lumen.domain.model.Point
import kotlin.math.hypot

class PointSmoother(private val baseFactor: Float = 0.8f) {
    private var lastPoint: Point? = null
    private val deadzone = 0.002f // Filtro de tremor microscópico

    fun smooth(current: Point): Point {
        val last = lastPoint ?: run {
            lastPoint = current
            return current
        }

        val dx = current.x - last.x
        val dy = current.y - last.y
        val dist = hypot(dx, dy)

        // 1. Filtro de Deadzone
        if (dist < deadzone) {
            return last
        }

        // 2. Fator Adaptativo baseado na Velocidade
        // Se mover rápido (dist alta), o adaptiveFactor fica menor (mais responsivo)
        // Se mover lento, o adaptiveFactor fica maior (mais suave)
        val velocity = (dist / 0.05f).coerceIn(0f, 1f)
        val adaptiveFactor = baseFactor * (1f - (velocity * 0.7f))

        val smoothed = Point(
            x = (current.x * (1f - adaptiveFactor)) + (last.x * adaptiveFactor),
            y = (current.y * (1f - adaptiveFactor)) + (last.y * adaptiveFactor)
        )
        
        lastPoint = smoothed
        return smoothed
    }

    fun reset() {
        lastPoint = null
    }
}
