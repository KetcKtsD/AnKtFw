package io.github.ketcktsd.anktfw.di.container

internal interface ContainerFactory<T : Any> {
    fun get(): Container<T>
}


internal class EachContainerFactory<T : Any>(private val initialize: () -> T,
                                             private val mode: InjectionMode) : ContainerFactory<T> {

    override fun get(): Container<T> = container(initialize, mode)
}

internal class SingletonContainerFactory<T : Any>(initialize: () -> T,
                                                  mode: InjectionMode) : ContainerFactory<T> {

    private val container = container(initialize, mode)

    override fun get(): Container<T> = container
}
