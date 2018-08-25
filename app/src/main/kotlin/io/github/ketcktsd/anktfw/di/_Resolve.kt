package io.github.ketcktsd.anktfw.di

import android.app.Activity
import io.github.ketcktsd.anktfw.MyApplication
import io.github.ketcktsd.anktfw.module

/**
 * resolve dependency by [MyApplication.module]
 */
inline fun <reified T : Any> Activity.resolve() = lazy { application.module.resolve(T::class).value }
