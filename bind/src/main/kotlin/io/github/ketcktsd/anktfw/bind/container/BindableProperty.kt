package io.github.ketcktsd.anktfw.bind.container

import kotlin.reflect.KProperty

interface ReadOnlyBindableProperty<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}

interface ReadWriteBindableProperty<T> : ReadOnlyBindableProperty<T> {
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}

private open class ReadOnlyBindablePropertyInternal<T>(
        initialValue: T,
        vararg containers: Bindable<T>
) : ReadOnlyBindableProperty<T> {
    protected var value: T = initialValue

    init {
        containers.forEach { container ->
            container.value = this.value
            container.onChange = { new ->
                this.value = new
            }
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}

private class ReadWriteBindablePropertyInternal<T>(
        initialValue: T,
        private vararg val containers: Bindable<T>
) : ReadOnlyBindablePropertyInternal<T>(initialValue, *containers), ReadWriteBindableProperty<T> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        containers.forEach { it.value = value }
    }
}

object BindableDelegates {

    fun <T> readOnly(initialValue: T, vararg containers: Bindable<T>): ReadOnlyBindableProperty<T> =
            ReadOnlyBindablePropertyInternal(initialValue, *containers)

    fun <T> readWrite(initialValue: T, vararg containers: Bindable<T>): ReadWriteBindableProperty<T> =
            ReadWriteBindablePropertyInternal(initialValue, *containers)
}
