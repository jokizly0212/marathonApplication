package fi.metropolia.marathon.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fi.metropolia.marathon.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //Reset theme after splash screen
        setTheme(R.style.marathonTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
