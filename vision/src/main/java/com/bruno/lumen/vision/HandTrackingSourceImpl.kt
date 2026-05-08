package com.bruno.lumen.vision

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.bruno.lumen.domain.model.Point
import com.bruno.lumen.domain.vision.HandInfo
import com.bruno.lumen.domain.vision.HandStatus
import com.bruno.lumen.domain.vision.HandTrackingSource
import com.bruno.lumen.domain.utils.PointSmoother
import com.bruno.lumen.vision.camera.CameraManager
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HandTrackingSourceImpl(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner
) : HandTrackingSource, HandTracker.HandTrackerListener {

    private val _handStatus = MutableStateFlow(HandStatus(isPresent = false))
    private val gestureProcessor = HandGestureProcessor()
    private val handTracker = HandTracker(context, this)
    
    // Mapa de suavizadores (um por mão)
    private val smoothers = mutableMapOf<Int, PointSmoother>()
    
    private val cameraManager = CameraManager(context, lifecycleOwner) { imageProxy ->
        handTracker.detectLiveStream(imageProxy)
    }

    override fun getHandStatus() = _handStatus.asStateFlow()

    override fun startTracking(previewSurface: Any?) {
        val surfaceProvider = previewSurface as? androidx.camera.core.Preview.SurfaceProvider
        cameraManager.startCamera(surfaceProvider)
    }

    override fun stopTracking() {
        cameraManager.shutdown()
        smoothers.clear()
    }

    override fun onResult(result: HandLandmarkerResult) {
        val gestureResults = gestureProcessor.processResult(result)
        
        val hands = gestureResults.mapIndexed { index, state ->
            val rawIndex = state.indexTip?.let { 
                Point(x = 1.0f - it.y, y = 1.0f - it.x)
            }
            val rawThumb = state.thumbTip?.let { 
                Point(x = 1.0f - it.y, y = 1.0f - it.x)
            }
            
            val smoother = smoothers.getOrPut(index) { PointSmoother(baseFactor = 0.3f) }
            val smoothedIndex = rawIndex?.let { smoother.smooth(it) }

            HandInfo(
                id = index,
                indexTip = smoothedIndex,
                thumbTip = rawThumb, // Polegar não precisa de suavização tão agressiva para Zoom
                gesture = state.gesture,
                confidence = state.confidence,
                side = state.side
            )
        }

        // Limpa suavizadores de mãos que sumiram
        if (hands.isEmpty()) smoothers.clear()

        _handStatus.value = HandStatus(
            isPresent = hands.isNotEmpty(),
            hands = hands
        )
    }

    override fun onError(error: String) {}
}
