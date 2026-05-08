package com.bruno.lumen.feature.drawing.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.bruno.lumen.domain.model.Stroke

object BitmapExporter {
    fun exportToBitmap(strokes: List<Stroke>, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Background
        canvas.drawColor(android.graphics.Color.BLACK)
        
        strokes.forEach { stroke ->
            val path = Path()
            stroke.points.forEachIndexed { index, point ->
                val x = point.x * width
                val y = point.y * height
                if (index == 0) path.moveTo(x, y)
                else path.lineTo(x, y)
            }
            
            val paint = Paint().apply {
                color = stroke.color
                strokeWidth = stroke.width
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
                isAntiAlias = true
            }
            
            // Draw Glow (Simplified for Bitmap)
            paint.alpha = 100
            paint.strokeWidth = stroke.width * 2f
            canvas.drawPath(path, paint)
            
            // Draw Core
            paint.alpha = 255
            paint.strokeWidth = stroke.width
            canvas.drawPath(path, paint)
        }
        
        return bitmap
    }
}
