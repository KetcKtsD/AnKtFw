package tech.ketc.anktfw

import android.app.Application
import tech.ketc.anktfw.di.SampleModule
import tech.ketc.anktfw.di.module.Module

class MyApplication : Application() {
    companion object {
        var module: Module = SampleModule()
    }

    override fun onCreate() {
        super.onCreate()
    }
}

val module = MyApplication.module