package io.github.ketcktsd.anktfw.bind

import android.widget.EditText
import android.widget.TextView
import io.github.ketcktsd.anktfw.bind.container.Bindable

fun TextView.bindable(): Bindable<CharSequence> = object : Bindable<CharSequence>() {
    override fun set(value: CharSequence) {
        text = value
    }

    override fun get(): CharSequence = text
}

fun EditText.bindable(): Bindable<CharSequence> = object : Bindable<CharSequence>() {
    override fun set(value: CharSequence) {
        setText(value)
    }

    override fun get() = text

}
