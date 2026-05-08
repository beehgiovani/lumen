package com.bruno.lumen.vision

import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import kotlin.math.pow
import kotlin.math.sqrt
import com.bruno.lumen.domain.model.Point
import com.bruno.lumen.domain.vision.Gesture

class HandGestureProcessor {

    data class HandState(
        val gesture: Gesture,
        val indexTip: Point?,
        val thumbTip: Point?,
        val confidence: Float,
        val side: String
    )

    fun processResult(result: HandLandmarkerResult): List<HandState> {
        val hands = mutableListOf<HandState>()
        
        result.landmarks().forEachIndexed { index, landmarks ->
            val handednessCategory = result.handedness()?.getOrNull(index)?.getOrNull(0)
            val handedness = handednessCategory?.categoryName() ?: "Unknown"
            val score = handednessCategory?.score() ?: 0f
            
            val wrist = landmarks[0]
            val indexTip = landmarks[8]
            val indexPip = landmarks[6]
            val thumbTip = landmarks[4]
            val middleTip = landmarks[12]
            val ringTip = landmarks[16]
            val pinkyTip = landmarks[20]
            
            fun distSq(a: com.google.mediapipe.tasks.components.containers.NormalizedLandmark, b: com.google.mediapipe.tasks.components.containers.NormalizedLandmark): Float {
                return (a.x() - b.x()).pow(2) + (a.y() - b.y()).pow(2)
            }

            val isIndexExtended = distSq(indexTip, wrist) > distSq(indexPip, wrist)
            val isMiddleExtended = distSq(middleTip, wrist) > distSq(landmarks[10], wrist)
            val isRingExtended = distSq(ringTip, wrist) > distSq(landmarks[14], wrist)
            val isPinkyExtended = distSq(pinkyTip, wrist) > distSq(landmarks[18], wrist)
            val pinchDistance = sqrt(distSq(thumbTip, indexTip))

            val gestureType = when {
                pinchDistance < 0.05f && !isMiddleExtended -> Gesture.PINCH_ZOOM
                isIndexExtended && !isMiddleExtended && !isRingExtended && !isPinkyExtended -> Gesture.POINTING
                isIndexExtended && isMiddleExtended && isRingExtended && isPinkyExtended -> Gesture.OPEN_HAND
                !isIndexExtended && !isMiddleExtended && !isRingExtended && !isPinkyExtended -> Gesture.FIST
                else -> Gesture.NONE
            }

            hands.add(HandState(
                gesture = gestureType,
                indexTip = Point(indexTip.x(), indexTip.y()),
                thumbTip = Point(thumbTip.x(), thumbTip.y()),
                confidence = score,
                side = handedness
            ))
        }
        
        return hands
    }
}
