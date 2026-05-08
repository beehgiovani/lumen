package com.bruno.lumen.domain.usecase

import com.bruno.lumen.domain.model.Point
import com.bruno.lumen.domain.model.DrawingRepository

class AddDrawingPointUseCase(private val repository: DrawingRepository) {
    operator fun invoke(point: Point) {
        repository.addPoint(point)
    }
}

class ClearCanvasUseCase(private val repository: DrawingRepository) {
    operator fun invoke() {
        repository.clear()
    }
}
