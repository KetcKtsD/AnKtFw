package io.github.ketcktsd.anktfw.di.module

/**
 * @see [DependencyProvider.lazySingleton]
 */
inline fun <reified T : Any> DependencyProvider.lazySingleton(noinline init: () -> T) =
        lazySingleton(T::class, init)

/**
 * @see [DependencyProvider.singleton]
 */
inline fun <reified T : Any> DependencyProvider.singleton(noinline init: () -> T) =
        singleton(T::class, init)

/**
 * @see [DependencyProvider.lazyEach]
 */
inline fun <reified T : Any> DependencyProvider.lazyEach(noinline init: () -> T) =
        lazyEach(T::class, init)

/**
 * @see [DependencyProvider.each]
 */
inline fun <reified T : Any> DependencyProvider.each(noinline init: () -> T) =
        each(T::class, init)