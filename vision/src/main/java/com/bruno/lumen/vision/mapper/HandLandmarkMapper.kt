package com.bruno.lumen.vision.mapper

import com.bruno.lumen.domain.model.Point
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult

class HandLandmarkMapper {
    fun mapToIndexTip(result: HandLandmarkerResult): Point? {
        val landmarks = result.landmarks().firstOrNull() ?: return null
        val indexTip = landmarks[8] // Index Tip
        return Point(indexTip.x(), indexTip.y())
    }
}
