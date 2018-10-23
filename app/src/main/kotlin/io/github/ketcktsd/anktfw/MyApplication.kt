package io.github.ketcktsd.anktfw

import android.app.Application
import io.github.ketcktsd.anktfw.di.module.Module
import io.github.ketcktsd.anktfw.di.sampleModule

class MyApplication : Application() {

    lateinit var module: Module
        private set


    override fun onCreate() {
        super.onCreate()
        module = sampleModule
    }
}

val Application.module: Module
    get() = (this as MyApplication).module
