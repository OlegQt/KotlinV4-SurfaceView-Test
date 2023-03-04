package App.kotlinv4.Engine

import android.graphics.PointF
import kotlin.random.Random

class CParticle(private val direction: PointF) {
    constructor() : this(PointF(0.0f, 0.0f))

    val position: PointF = PointF()
    private val velocity: PointF = PointF()
    private var mass: Float = 1.0f
    private var gi: Float = 9.8f
    //private var time: Long = 0
    var toDelete = false

    init {
        position.x = Random.nextFloat()
        position.y = Random.nextFloat()
    }

    fun update(time: Long) {
        // Move the particle
        velocity.y += (gi/10000 * time / 1000)

        position.y += velocity.y
        checkBounds(time)
    }

    private fun checkBounds(time: Long) {
        val nextYpos = position.y + velocity.y
        if (nextYpos >= 1000 && position.y < 1000) {
            velocity.y = -velocity.y * 0.7f
        }
        toDelete = position.y>1000
    }
}