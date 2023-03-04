package App.kotlinv4.Engine

import android.util.Log

class CEngine {
    private var count: Long = 0;
    private var counting: Boolean = false
    val sys: ParticleSystem = ParticleSystem()

    init {
        Log.i("MyActivity","Engine Sys init")

    }

    // Check


    fun update(frameTime: Long) {
        //Check
        sys.updateAllParticles(frameTime)
    }

    fun click() {
        //Click from MainActivity button
        sys.addRandomParticle()
    }
}