package com.bruno.lumen.feature.drawing.ui

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.bruno.lumen.feature.drawing.vm.DrawingViewModel

@Composable
fun CameraDrawingOverlay(
    viewModel: DrawingViewModel,
    modifier: Modifier = Modifier
) {
    val strokes by viewModel.strokes.collectAsState()


    val lifecycleOwner = LocalLifecycleOwner.current

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView<PreviewView>(
            factory = { ctx ->
                PreviewView(ctx).also { previewView ->
                    previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    // Pass the surface provider to the tracking engine
                    viewModel.startTracking(previewView.surfaceProvider)
                }
            },
            modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0f)
        )

        // Drawing Canvas Overlay
        val currentTool by viewModel.currentTool.collectAsState()
        val currentWidth by viewModel.currentWidth.collectAsState()
        val currentColor by viewModel.currentColor.collectAsState()
        val handStatus by viewModel.handStatus.collectAsState(
            initial = com.bruno.lumen.domain.vision.HandStatus(isPresent = false)
        )

        DrawingCanvas(
            strokes = strokes,
            handStatus = handStatus,
            currentTool = currentTool,
            currentWidth = currentWidth,
            currentColor = currentColor,
            modifier = Modifier.fillMaxSize()
        )

        HandOverlay(handStatus = handStatus)
    }
}
