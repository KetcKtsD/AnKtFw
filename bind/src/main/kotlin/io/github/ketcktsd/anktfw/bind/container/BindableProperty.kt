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
        private vararg val bindables: Bindable<T>
) : ReadOnlyBindableProperty<T> {
    protected var value: T = initialValue
    private var mInitialized = false
    private val mOnChange: (T) -> Unit = { new ->
        this.value = new
    }

    protected fun initialize() {
        if (mInitialized) return
        mInitialized = true
        bindables.forEach { bindable ->
            bindable.value = this.value
            bindable.onChange = mOnChange
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        initialize()
        return value
    }
}

private class ReadWriteBindablePropertyInternal<T>(
        initialValue: T,
        private vararg val bindables: Bindable<T>
) : ReadOnlyBindablePropertyInternal<T>(initialValue, *bindables), ReadWriteBindableProperty<T> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        initialize()
        this.value = value
        bindables.forEach { it.setInternal(value) }
    }
}

object BindableDelegates {

    fun <T> readOnly(initialValue: T, vararg containers: Bindable<T>): ReadOnlyBindableProperty<T> =
            ReadOnlyBindablePropertyInternal(initialValue, *containers)

    fun <T> readWrite(initialValue: T, vararg containers: Bindable<T>): ReadWriteBindableProperty<T> =
            ReadWriteBindablePropertyInternal(initialValue, *containers)
}
