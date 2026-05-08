package com.bruno.lumen.feature.drawing.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.bruno.lumen.domain.model.Stroke

class BitmapExporter {
    fun createBitmapFromStrokes(strokes: List<Stroke>, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Fundo (Black por padrão no Lúmen)
        canvas.drawColor(android.graphics.Color.BLACK)
        
        strokes.forEach { stroke ->
            val paint = Paint().apply {
                color = stroke.color
                strokeWidth = stroke.width
                style = Paint.Style.STROKE
                strokeJoin = Paint.Join.ROUND
                strokeCap = Paint.Cap.ROUND
                isAntiAlias = true
            }
            
            val path = Path()
            stroke.points.forEachIndexed { index, point ->
                if (index == 0) path.moveTo(point.x, point.y)
                else path.lineTo(point.x, point.y)
            }
            
            // Efeito Neon (Glow) Simplificado para o PNG
            paint.alpha = 100
            paint.strokeWidth = stroke.width * 2f
            canvas.drawPath(path, paint)
            
            paint.alpha = 255
            paint.strokeWidth = stroke.width
            canvas.drawPath(path, paint)
        }
        
        return bitmap
    }
}
