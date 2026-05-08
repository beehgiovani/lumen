package com.bruno.lumen.data.repository

import com.bruno.lumen.domain.model.DrawingRepository
import com.bruno.lumen.domain.model.Point
import com.bruno.lumen.domain.model.Stroke
import com.bruno.lumen.domain.utils.PointSmoother
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DrawingRepositoryImpl : DrawingRepository {
    private val _strokes = MutableStateFlow<List<Stroke>>(emptyList())
    val strokesFlow = _strokes.asStateFlow()

    private val redoStack = mutableListOf<Stroke>()
    
    private val smoother = PointSmoother(baseFactor = 0.25f)
    private var currentPoints = mutableListOf<Point>()
    private var currentColor: Int = 0
    private var currentWidth: Float = 10f
    private var currentType: String = "NEON"

    override fun startNewStroke(color: Int, width: Float, type: String) {
        smoother.reset()
        currentPoints = mutableListOf()
        currentColor = color
        currentWidth = width
        currentType = type
        redoStack.clear() // Clear redo history when starting new work
    }

    override fun addPoint(point: Point) {
        val smoothedPoint = smoother.smooth(point)
        currentPoints.add(smoothedPoint)
        
        val newStroke = Stroke(currentPoints.toList(), currentColor, currentWidth, currentType)
        val currentList = _strokes.value.toMutableList()
        
        if (currentList.isNotEmpty() && currentPoints.size > 1) {
            currentList[currentList.size - 1] = newStroke
        } else {
            currentList.add(newStroke)
        }
        
        _strokes.value = currentList
    }

    override fun undo() {
        val currentList = _strokes.value.toMutableList()
        if (currentList.isNotEmpty()) {
            val removed = currentList.removeAt(currentList.size - 1)
            redoStack.add(removed)
            _strokes.value = currentList
        }
    }

    override fun redo() {
        if (redoStack.isNotEmpty()) {
            val stroke = redoStack.removeAt(redoStack.size - 1)
            val currentList = _strokes.value.toMutableList()
            currentList.add(stroke)
            _strokes.value = currentList
        }
    }

    override fun eraseAt(point: Point) {
        val eraserRadiusSq = 0.0025f // 0.05 ^ 2 (coordenadas normalizadas)
        val currentList = _strokes.value
        var globalChanged = false

        val updatedList = currentList.map { stroke ->
            var strokeChanged = false
            val filteredPoints = stroke.points.filter { p ->
                val dx = p.x - point.x
                val dy = p.y - point.y
                val distSq = dx * dx + dy * dy
                if (distSq < eraserRadiusSq) {
                    strokeChanged = true
                    globalChanged = true
                    false
                } else {
                    true
                }
            }
            if (strokeChanged) stroke.copy(points = filteredPoints) else stroke
        }.filter { it.points.size > 1 }

        if (globalChanged) {
            _strokes.value = updatedList
            redoStack.clear()
        }
    }

    override fun getStrokes(): List<Stroke> = _strokes.value

    override fun clear() {
        _strokes.value = emptyList()
        currentPoints.clear()
        redoStack.clear()
        smoother.reset()
    }
}
