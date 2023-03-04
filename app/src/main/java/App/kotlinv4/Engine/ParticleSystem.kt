package App.kotlinv4.Engine

import android.util.Log

class ParticleSystem {
    val pSys: MutableList<CParticle> = ArrayList()
    //private val book:MutableList<Int> = mutableListOf(10)
    init {
        Log.i("MyActivity","Particle Sys init")
    }

    fun addRandomParticle() {
        //val particle:CParticle = CParticle()
        for (i in 0 until 100){
            pSys.add(CParticle())
        }
    }

    fun updateAllParticles(time: Long) {
        val iterator = pSys.iterator()
        while (iterator.hasNext()) {
            val particle = iterator.next()
            particle.update(time)
            if(particle.toDelete) iterator.remove()
            //check if particle should be deleted
        }
    }
}