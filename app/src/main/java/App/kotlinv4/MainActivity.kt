package App.kotlinv4

import App.kotlinv4.Engine.Screen
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var txt:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        setContentView(Screen(this))

        txt=findViewById(R.id.txtInfo)
        val strInfo:String = "New Application"
        txt?.text=strInfo


    }
}