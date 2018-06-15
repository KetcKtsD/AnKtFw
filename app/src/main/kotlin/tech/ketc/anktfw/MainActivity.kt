package tech.ketc.anktfw

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import tech.ketc.anktfw.di.DISampleActivity

class MainActivity : AppCompatActivity(), IMainUI by MainUI() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        setSupportActionBar(toolbar)
        startActivity(Intent(this, DISampleActivity::class.java))
    }
}
