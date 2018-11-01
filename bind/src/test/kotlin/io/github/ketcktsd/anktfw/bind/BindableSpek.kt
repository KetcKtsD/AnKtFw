package io.github.ketcktsd.anktfw.bind

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class BindableSpek : Spek({
    it("readOnly: bind from bindable") {
        var data1 = "data1"
        var data2 = "data2"

        val dataValue1 = bindable({ data1 = it }, { data1 })
        val dataValue2 = bindable({ data2 = it }, { data2 })

        val dataValueArray = arrayOf(dataValue1, dataValue2)
        val readOnly: String by BindableDelegates
                .readOnly("", *dataValueArray)

        assertEquals("data1", data1)
        assertEquals("data2", data2)

        fun checkBind(value: String) {
            assertEquals(value, readOnly)
            assertEquals(value, data1)
            assertEquals(value, data2)
            assertEquals(data1, dataValue1.value)
            assertEquals(data2, dataValue2.value)
        }

        dataValue1.value = "bind1"
        checkBind("bind1")

        dataValue2.value = "bind2"
        checkBind("bind2")
    }

    it("readOnly: bind to bindable (at readOnly initialization)") {
        var data1 = "data1"
        var data2 = "data2"

        val dataValue1 = bindable({ data1 = it }, { data1 })
        val dataValue2 = bindable({ data2 = it }, { data2 })

        val dataValueArray = arrayOf(dataValue1, dataValue2)
        val readOnly: String by BindableDelegates
                .readOnly("", *dataValueArray)

        assertEquals("data1", data1)
        assertEquals("data2", data2)

        fun checkBind(value: String) {
            assertEquals(value, readOnly)
            assertEquals(value, data1)
            assertEquals(value, data2)
            assertEquals(data1, dataValue1.value)
            assertEquals(data2, dataValue2.value)
        }

        //読み込んだ時点で初期化され､かつバインドされるか
        @Suppress("UNUSED_VARIABLE")
        val readReadOnly = readOnly
        checkBind("")

        dataValue1.value = "bind1"
        checkBind("bind1")
    }

    it("readWrite: bind from bindable > bind to bindable") {
        var data1 = "data1"
        var data2 = "data2"

        val dataValue1 = bindable({ data1 = it }, { data1 })
        val dataValue2 = bindable({ data2 = it }, { data2 })

        val dataValueArray = arrayOf(dataValue1, dataValue2)
        var readWrite: String by BindableDelegates
                .readWrite("", *dataValueArray)

        assertEquals("data1", data1)
        assertEquals("data2", data2)

        fun checkBind(value: String) {
            assertEquals(value, readWrite)
            assertEquals(value, data1)
            assertEquals(value, data2)
            assertEquals(data1, dataValue1.value)
            assertEquals(data2, dataValue2.value)
        }


        dataValue1.value = "bind1"
        checkBind("bind1")

        dataValue2.value = "bind2"
        checkBind("bind2")

        readWrite = "bind3"
        checkBind("bind3")
    }
    it("readWrite: bind to bindable(at readWrite initialization) > bind from bindable") {
        var data1 = "data1"
        var data2 = "data2"

        val dataValue1 = bindable({ data1 = it }, { data1 })
        val dataValue2 = bindable({ data2 = it }, { data2 })

        val dataValueArray = arrayOf(dataValue1, dataValue2)
        var readWrite: String by BindableDelegates
                .readWrite("", *dataValueArray)

        assertEquals("data1", data1)
        assertEquals("data2", data2)

        fun checkBind(value: String) {
            assertEquals(value, readWrite)
            assertEquals(value, data1)
            assertEquals(value, data2)
            assertEquals(data1, dataValue1.value)
            assertEquals(data2, dataValue2.value)
        }

        //読み込んだ時点で初期化され､かつバインドされるか
        @Suppress("UNUSED_VARIABLE")
        val readReadWrite = readWrite
        checkBind("")

        readWrite = "bind1"
        checkBind("bind1")

        dataValue1.value = "bind2"
        checkBind("bind2")

        dataValue2.value = "bind3"
        checkBind("bind3")
    }
})
