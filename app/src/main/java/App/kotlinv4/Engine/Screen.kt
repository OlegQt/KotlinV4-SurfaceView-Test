package App.kotlinv4.Engine

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView

class Screen @JvmOverloads constructor(context: Context,attributeSet: AttributeSet,defStyleAttr:Int=0):
    SurfaceView(context,attributeSet,defStyleAttr),Runnable {
    private val tag = "MyActivity"
    private var isRunning = true

    init {
        val thread:Thread = Thread(this)
        thread.start()
        Log.i(tag,"Init выполнен")
    }

    private fun render(canvas: Canvas){
        var paint:Paint = Paint()
        canvas.drawColor(Color.WHITE);

        paint.color = Color.BLACK
        paint.textSize = 30.0f
        canvas.drawRect(100.0f,100.0f,200.0f,200.0f,paint)
        canvas.drawText("Success",250.0f,150.0f,paint)
    }



    override fun run() {
        while(isRunning) {
            if (holder.surface.isValid) {
                val canvas: Canvas = holder.lockCanvas()
                this.render(canvas)
                holder.unlockCanvasAndPost(canvas);
                isRunning = false
            }
        }
    }

}