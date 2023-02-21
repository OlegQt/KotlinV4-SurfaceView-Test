package App.kotlinv4.Engine

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView
import kotlin.concurrent.thread

class Screen @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0
) : SurfaceView(context, attributeSet, defStyleAttr), Runnable {
    private val tag = "MyActivity"
    private var isRunning = true
    private var thread: Thread? = null
    private var engine: Clogic? = null

    init {
        Log.i(tag, "Init выполнен")
    }

    private fun render(canvas: Canvas) {
        var paint: Paint = Paint()
        canvas.drawColor(Color.GRAY);

        paint.color = Color.BLACK
        paint.textSize = 30.0f
        canvas.drawRect(100.0f, 100.0f, 200.0f, 200.0f, paint)
        canvas.drawText(this.engine.toString(), 250.0f, 150.0f, paint)
    }

    fun pause() {
        isRunning = false
        try {
            // Stop the thread (rejoin the main thread)
            thread?.join()
            Log.w(tag,"thread stopped")
        } catch (error: InterruptedException) {
            Log.i(tag,error.toString())
        }
    }

    fun resume() {
        isRunning = true

        thread = Thread(this)
        thread?.start()
        Log.i(tag,"thread resumed")
    }

    fun setLogic(logic: Clogic) {
        this.engine = logic
    }

    override fun run() {
        while (isRunning) {
            if (holder.surface.isValid) {
                val canvas: Canvas = holder.lockCanvas()
                this.render(canvas)
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}