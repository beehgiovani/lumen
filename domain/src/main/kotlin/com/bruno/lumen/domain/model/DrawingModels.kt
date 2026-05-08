package com.bruno.lumen.domain.model

data class Point(val x: Float, val y: Float)

data class Stroke(
    val points: List<Point>,
    val color: Int,
    val width: Float,
    val type: String = "NEON"
)

interface DrawingRepository {
    fun addPoint(point: Point)
    fun startNewStroke(color: Int, width: Float, type: String = "NEON")
    fun getStrokes(): List<Stroke>
    fun undo()
    fun redo()
    fun eraseAt(point: Point)
    fun clear()
}
