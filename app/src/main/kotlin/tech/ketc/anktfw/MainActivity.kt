package tech.ketc.anktfw

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.ketc.anktfw.di.DISampleActivity

class MainActivity : AppCompatActivity(), IMainUI by MainUI() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        setSupportActionBar(toolbar)
        startActivity(Intent(this, DISampleActivity::class.java))
    }
}
