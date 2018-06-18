package tech.ketc.anktfw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity(), IMainUI by MainUI() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)
        setSupportActionBar(appbarComponent.toolbar)
    }
}
