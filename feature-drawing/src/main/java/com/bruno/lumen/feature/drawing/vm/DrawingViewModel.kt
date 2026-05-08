package com.bruno.lumen.feature.drawing.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno.lumen.data.repository.DrawingRepositoryImpl
import com.bruno.lumen.domain.model.Point
import com.bruno.lumen.domain.model.Stroke
import com.bruno.lumen.domain.usecase.AddDrawingPointUseCase
import com.bruno.lumen.domain.usecase.ClearCanvasUseCase
import com.bruno.lumen.domain.vision.HandTrackingSource
import com.bruno.lumen.domain.vision.Gesture
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.hypot

class DrawingViewModel(
    private val repository: DrawingRepositoryImpl = DrawingRepositoryImpl(),
    private val handTrackingSource: HandTrackingSource
) : ViewModel() {

    private val lastGestures = mutableMapOf<Int, Gesture>()
    private var backHandStartTime: Long = 0
    private var lastPinchDist: Float = 0f

    private val addPointUseCase = AddDrawingPointUseCase(repository)
    private val clearUseCase = ClearCanvasUseCase(repository)

    val strokes: StateFlow<List<Stroke>> = repository.strokesFlow
    val handStatus = handTrackingSource.getHandStatus()

    enum class Tool { 
        BRUSH, ERASER, CIRCLE, CUBE_3D, CRYSTAL, 
        LIGHTNING, RAINBOW, GALAXY, FIRE, WEB, 
        CALLIGRAPHY, SHIMMER, HEART, STAR, SQUARE, TRIANGLE,
        SPRAY, AIRBRUSH, PARTICLES, RIBBON, NEON_PULSE
    }
    
    private val _currentTool = MutableStateFlow(Tool.BRUSH)
    val currentTool: StateFlow<Tool> = _currentTool.asStateFlow()

    private val _currentColor = MutableStateFlow(0xFF00E5FF.toInt())
    val currentColor: StateFlow<Int> = _currentColor.asStateFlow()

    private val _currentWidth = MutableStateFlow(12f)
    val currentWidth: StateFlow<Float> = _currentWidth.asStateFlow()

    private val _isRainbowMode = MutableStateFlow(false)
    val isRainbowMode: StateFlow<Boolean> = _isRainbowMode.asStateFlow()
    
    private val _isMirrorMode = MutableStateFlow(false)
    val isMirrorMode: StateFlow<StateFlow<Boolean>> = MutableStateFlow(MutableStateFlow(false)) // Placeholder for UI consistency
    
    private val _isKaleidoscope = MutableStateFlow(false)
    private val _isEchoMode = MutableStateFlow(false)

    private val _zoomScale = MutableStateFlow(1f)
    val zoomScale: StateFlow<Float> = _zoomScale.asStateFlow()

    private var rainbowHue = 0f

    init {
        viewModelScope.launch {
            handStatus.collect { status ->
                status.hands.forEach { hand ->
                    val tip = hand.indexTip ?: return@forEach
                    
                    when (hand.gesture) {
                        Gesture.POINTING -> {
                            backHandStartTime = 0L
                            if (_currentTool.value == Tool.ERASER) {
                                repository.eraseAt(tip)
                            } else {
                                if (_isRainbowMode.value) {
                                    rainbowHue = (rainbowHue + 1f) % 360f
                                    _currentColor.value = Color.hsv(rainbowHue, 1f, 1f).toArgb()
                                }
                                
                                if (repository.getStrokes().isEmpty() || (hand.gesture != lastGestures[hand.id])) {
                                    repository.startNewStroke(_currentColor.value, _currentWidth.value, _currentTool.value.name)
                                }
                                
                                addPointUseCase(tip)
                            }
                        }
                        Gesture.FIST -> {
                            backHandStartTime = 0L
                            repository.eraseAt(tip)
                        }
                        Gesture.BACK_HAND -> {
                            if (backHandStartTime == 0L) backHandStartTime = System.currentTimeMillis()
                            if (System.currentTimeMillis() - backHandStartTime > 1500) {
                                clear()
                                backHandStartTime = 0L
                            }
                        }
                        Gesture.PINCH_ZOOM -> {
                            backHandStartTime = 0L
                            val index = hand.indexTip
                            val thumb = hand.thumbTip
                            if (index != null && thumb != null) {
                                val dist = hypot(index.x - thumb.x, index.y - thumb.y)
                                if (lastPinchDist > 0) {
                                    val delta = (dist - lastPinchDist) * 3f
                                    _zoomScale.value = (_zoomScale.value + delta).coerceIn(0.5f, 5.0f)
                                }
                                lastPinchDist = dist
                            }
                        }
                        else -> {
                            backHandStartTime = 0L
                            lastPinchDist = 0f
                        }
                    }
                    lastGestures[hand.id] = hand.gesture
                }
            }
        }
    }

    fun selectTool(tool: Tool) { _currentTool.value = tool }
    fun updateColor(colorArgb: Int) { _currentColor.value = colorArgb }
    fun updateBrushSize(size: Float) { _currentWidth.value = size }
    fun undo() = repository.undo()
    fun redo() = repository.redo()
    fun toggleRainbowMode() { _isRainbowMode.value = !_isRainbowMode.value }
    fun startTracking(surface: Any? = null) = handTrackingSource.startTracking(surface)
    fun stopTracking() = handTrackingSource.stopTracking()

    fun clear() {
        viewModelScope.launch { clearUseCase() }
    }
}
