package io.github.ketcktsd.anktfw.di

import android.app.Activity
import io.github.ketcktsd.anktfw.module

inline fun <reified T : Any> Activity.resolve() = lazy { application.module.resolve(T::class).value }
