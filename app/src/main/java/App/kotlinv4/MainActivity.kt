package App.kotlinv4

import App.kotlinv4.Engine.Screen
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var txt:TextView?=null
    private var btn:Button?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val screen:Screen = findViewById(R.id.screen)
        txt=findViewById(R.id.txtInfo)
        btn=findViewById(R.id.btnAction)

        btn?.setOnClickListener { txt?.text="Action" }

    }
}