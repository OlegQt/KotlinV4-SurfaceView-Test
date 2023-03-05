package App.kotlinv4.Engine

import android.graphics.PointF
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


open class DxPoint(x: Float, y: Float, private var z: Float) : PointF(x, y) {
    protected var level = 0f
    protected val pointRadius: Float
        get() = 1 / z

    fun rotatePoint(angle: Float) {
        // Умножаем на матрицу поворота вектора на угол
        val x1 = (x * cos(angle.toDouble()) + y * sin(angle.toDouble())).toFloat()
        val y1 = (y * cos(angle.toDouble()) - x * sin(angle.toDouble())).toFloat()
        x = x1
        y = y1
    }

    fun get2dPoint(distance: Float, hW: Float, hH: Float): PointF {
        // Учет расстояния от точки до экрана
        var xPos = x / z
        var yPos = y / z
        // Транслирование координат точки на плоскость экрана
        xPos = xPos * hW + hW
        yPos = hH - yPos * hH
        return PointF(xPos, yPos)
    }
}