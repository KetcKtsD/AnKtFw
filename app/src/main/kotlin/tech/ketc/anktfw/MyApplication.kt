package tech.ketc.anktfw

import android.app.Application
import tech.ketc.anktfw.di.SampleModule
import tech.ketc.anktfw.di.module.Module

class MyApplication : Application() {

    lateinit var module: Module
        private set


    override fun onCreate() {
        super.onCreate()
        module = SampleModule()
    }
}

val Application.module: Module
    get() = (this as MyApplication).module