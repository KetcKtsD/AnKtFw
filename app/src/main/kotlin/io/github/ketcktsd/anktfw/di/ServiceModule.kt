package io.github.ketcktsd.anktfw.di

import io.github.ketcktsd.anktfw.di.module.Module
import io.github.ketcktsd.anktfw.di.module.each

val serviceModule = Module {
    each<HttpURLConnectionFactory> { DefaultHttpURLConnectionFactory() }
}
