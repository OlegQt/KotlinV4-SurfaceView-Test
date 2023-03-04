package App.kotlinv4.Engine

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView

class Screen @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0
) : SurfaceView(context, attributeSet, defStyleAttr), Runnable {
    private val tag = "MyActivity"
    private var isRunning = true
    private var paused = false
    private var thread: Thread? = null // Поток
    private var engine: CEngine = CEngine() // Класс движка игры
    private var canvas: Canvas = Canvas()
    private var paint: Paint = Paint()
    private var btn: RectF = RectF()

    // How many frames per second did we get?
    private var fps: Long = 0

    // The number of milliseconds in a seconds
    private val millisInSecond: Long = 1000

    init {
        btn = RectF(10f, 80f, 100f, 150f)
        Log.i(tag, "Screen: Init выполнен")
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            render()
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun render() {
        //canvas.drawColor(Color.GRAY)
        renderBounds()
        renderParticle()
        printDebuggingText()
    }

    private fun renderBounds() {
        val bounds = RectF(0.0f, 0.0f, canvas.width.toFloat(), canvas.height.toFloat())

        paint.style = Paint.Style.FILL
        paint.color = Color.rgb(255, 159, 128)
        canvas.drawRect(bounds, paint)

        // Draw the buttons
        //paint.style = Paint.Style.FILL
        //paint.color = Color.DKGRAY
        //paint.strokeWidth = 1.0f
        //canvas.drawRect(btn, paint)
    }

    private fun renderParticle() {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 8.0f
        //canvas.drawPoint(engine.particle.position.x,engine.particle.position.y,paint)
        engine.sys.pSys.forEach { particle ->
            val point = tpts(particle.position.x,particle.position.y,0.0f)

            canvas.drawPoint(point.x, point.y, paint)
        }
    }

    private fun printDebuggingText() {
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        paint.strokeWidth = 1.0f
        paint.textSize = 30.0f

        canvas.drawText("FPS $fps", 10.0f, 30.0f, paint)
        canvas.drawText("N ${engine.sys.pSys.size}", 10.0f, 55.0f, paint)
    }

    fun tpts(x:Float,y:Float,z:Float): PointF {
        val point:PointF = PointF()
        point.x = x*this.width
        point.y = this.height-y*height
        return point
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

    fun setLogic(logic: CEngine) {
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
            draw()
            // if (!paused) update(frameTime) //Эта функция была здесь

            // Store the fps in millis
            val frameTime = System.currentTimeMillis() - frameStartTime
            if (frameTime > 0) {
                fps = this.millisInSecond / frameTime
                if (!paused) update(frameTime)
            }
        }
    }

    private fun update(frameTime: Long) {
        engine.update(frameTime)
    }
}
