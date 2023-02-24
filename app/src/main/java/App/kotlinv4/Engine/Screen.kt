package App.kotlinv4.Engine

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView

class Screen @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0
) : SurfaceView(context, attributeSet, defStyleAttr), Runnable {
    private val tag = "MyActivity"
    private var isRunning = true
    private var paused = false
    private var thread: Thread? = null
    private var engine: Clogic? = null
    private var canvas: Canvas = Canvas()
    private var paint: Paint = Paint()

    // How many frames per second did we get?
    private var fps: Long = 0

    // The number of milliseconds in a seconds
    private val millisInSecond: Long = 1000

    init {
        Log.i(tag, "Init выполнен")
    }

    private fun draw(){
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            render()
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private fun render() {
        canvas.drawColor(Color.GRAY);

        paint.color = Color.BLACK
        paint.textSize = 30.0f
        canvas.drawRect(100.0f, 100.0f, 200.0f, 200.0f, paint)
        canvas.drawText(engine?.toString() ?: "No engine found", 250.0f, 150.0f, paint)
        printDebuggingText()
    }

    private fun printDebuggingText() {
        canvas.drawText("FPS $fps", 10.0f, 30.0f, paint)
    }

    fun pause() {
        isRunning = false
        try {
            // Stop the thread (rejoin the main thread)
            thread?.join()

            // Logging
            Log.w(tag, "${thread?.name ?: "no thread"} joined")
        } catch (error: InterruptedException) {
            Log.e(tag, error.toString()) // Logging
        }
    }

    fun resume() {
        isRunning = true

        thread = Thread(this, "RenderThread")
        thread?.start()

        // Logging
        Log.i(tag, thread?.name ?: "Error starting thread")
    }

    fun setLogic(logic: Clogic) {
        this.engine = logic
    }

    override fun run() {
        // When we start the thread with:
        // thread.start();
        // the run function is continuously called by Android
        // because we implemented the Runnable interface
        // Calling thread.join();
        // will stop the thread
        while (isRunning) {
            val frameStartTime = System.currentTimeMillis()
            if(!paused){
                update()

            }
            draw()
            // Store the fps in millis
            val frameTime = System.currentTimeMillis() - frameStartTime
            if (frameTime > 0) fps = this.millisInSecond / frameTime
        }
    }

    private fun update(){

    }
}
