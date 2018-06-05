package tech.ketc.anktfw

import android.app.Application
import tech.ketc.anktfw.di.SampleModule
import tech.ketc.anktfw.di.module.Module

class MyApplication : Application() {

    companion object {
        private lateinit var cModule: Module
        val module: Module
            get() = cModule

    }

    override fun onCreate() {
        super.onCreate()
        cModule = SampleModule()
    }
}