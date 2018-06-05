package tech.ketc.anktfw.di

import android.app.Activity
import tech.ketc.anktfw.module

inline fun <reified T : Any> Activity.resolve() = lazy { application.module.resolve(T::class).value }