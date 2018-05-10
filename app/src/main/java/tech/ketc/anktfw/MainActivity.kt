package tech.ketc.anktfw

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import tech.ketc.anktfw.androidarch.AsyncSampleActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, AsyncSampleActivity::class.java))
    }
}
