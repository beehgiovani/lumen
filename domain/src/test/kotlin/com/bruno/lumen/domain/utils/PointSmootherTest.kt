package com.bruno.lumen.domain.utils

import com.bruno.lumen.domain.model.Point
import org.junit.Assert.assertEquals
import org.junit.Test

class PointSmootherTest {

    @Test
    fun `test smoothing logic`() {
        val smoother = PointSmoother(factor = 0.5f)
        val p1 = Point(0f, 0f)
        val p2 = Point(10f, 10f)

        // First point should not be smoothed (no previous point)
        val s1 = smoother.smooth(p1)
        assertEquals(0f, s1.x, 0.01f)

        // Second point: factor * current + (1 - factor) * last
        // 0.5 * 10 + 0.5 * 0 = 5
        val s2 = smoother.smooth(p2)
        assertEquals(5f, s2.x, 0.01f)
        assertEquals(5f, s2.y, 0.01f)
    }

    @Test
    fun `test reset`() {
        val smoother = PointSmoother(factor = 0.5f)
        smoother.smooth(Point(10f, 10f))
        smoother.reset()
        
        val s = smoother.smooth(Point(20f, 20f))
        assertEquals(20f, s.x, 0.01f) // Should start fresh
    }
}
