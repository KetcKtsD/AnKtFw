package io.github.ketcktsd.anktfw

import android.app.Application
import io.github.ketcktsd.anktfw.di.SampleModule
import io.github.ketcktsd.anktfw.di.module.Module

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
