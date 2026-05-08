package com.bruno.lumen.domain.vision

import com.bruno.lumen.domain.model.Point
import kotlinx.coroutines.flow.Flow

enum class Gesture {
    POINTING,
    OPEN_HAND,
    FIST,
    PINCH_ZOOM,
    BACK_HAND,
    NONE
}

data class HandInfo(
    val id: Int,
    val indexTip: Point?,
    val thumbTip: Point? = null,
    val gesture: Gesture,
    val confidence: Float = 0f,
    val side: String = "Unknown" // "Left" ou "Right"
)

data class HandStatus(
    val isPresent: Boolean,
    val hands: List<HandInfo> = emptyList()
)

interface HandTrackingSource {
    fun getHandStatus(): Flow<HandStatus>
    fun startTracking(previewSurface: Any? = null)
    fun stopTracking()
}
