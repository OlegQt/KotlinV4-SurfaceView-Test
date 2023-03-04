package App.kotlinv4

import App.kotlinv4.Engine.CEngine
import App.kotlinv4.Engine.Screen
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var txt: TextView? = null
    private var btn: Button? = null
    private var screen: Screen? = null
    private var engine: CEngine = CEngine()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screen = findViewById(R.id.screen)
        txt = findViewById(R.id.txtInfo)
        btn = findViewById(R.id.btnAction)

        screen?.setLogic(engine)

        btn?.setOnClickListener {
            this.engine.click()
        }

    }

    override fun onPause() {
        super.onPause()
        screen?.pause()

    }

    override fun onResume() {
        super.onResume()
        screen?.resume()
    }

}