package com.bruno.lumen.vision

import android.content.Context
import android.os.SystemClock
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult

class HandTracker(
    private val context: Context,
    private val listener: HandTrackerListener
) {
    private var handLandmarker: HandLandmarker? = null

    init {
        setupHandLandmarker()
    }

    private fun setupHandLandmarker() {
        val baseOptionsBuilder = BaseOptions.builder()
            .setDelegate(com.google.mediapipe.tasks.core.Delegate.GPU)
            .setModelAssetPath("hand_landmarker.task")

        val optionsBuilder = HandLandmarker.HandLandmarkerOptions.builder()
            .setBaseOptions(baseOptionsBuilder.build())
            .setMinHandDetectionConfidence(0.3f)
            .setMinTrackingConfidence(0.3f)
            .setMinHandPresenceConfidence(0.3f)
            .setNumHands(1)
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setResultListener { result, _ ->
                listener.onResult(result)
            }

        handLandmarker = HandLandmarker.createFromOptions(context, optionsBuilder.build())
    }

    fun detectLiveStream(imageProxy: ImageProxy) {
        val bitmap = imageProxy.toBitmap()
        val mpImage = BitmapImageBuilder(bitmap).build()
        handLandmarker?.detectAsync(mpImage, SystemClock.uptimeMillis())
        imageProxy.close()
    }

    interface HandTrackerListener {
        fun onResult(result: HandLandmarkerResult)
        fun onError(error: String)
    }
}
