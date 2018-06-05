package tech.ketc.anktfw.di

import tech.ketc.anktfw.MyApplication

inline fun <reified T : Any> resolve() = MyApplication.module.resolve(T::class)