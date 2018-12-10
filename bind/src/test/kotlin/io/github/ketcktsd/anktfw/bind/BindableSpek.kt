package io.github.ketcktsd.anktfw.bind

import io.github.ketcktsd.anktfw.bind.collective.*
import io.github.ketcktsd.anktfw.bind.property.*
import org.jetbrains.spek.api.*
import org.jetbrains.spek.api.dsl.*
import org.junit.platform.runner.*
import org.junit.runner.*
import kotlin.test.*

@RunWith(JUnitPlatform::class)
class BindableSpek : Spek({
    it("readOnly: bind from observable") {
        var data1 = "data1"
        var data2 = "data2"

        val dataValue1 = observable({ data1 = it }, { data1 })
        val dataValue2 = observable({ data2 = it }, { data2 })

        val dataValueArray = arrayOf(dataValue1, dataValue2)
        val readOnly: String by bindCollective("", *dataValueArray)

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

    it("readOnly: bind to observable (at readOnly initialization)") {
        var data1 = "data1"
        var data2 = "data2"

        val dataValue1 = observable({ data1 = it }, { data1 })
        val dataValue2 = observable({ data2 = it }, { data2 })

        val dataValueArray = arrayOf(dataValue1, dataValue2)
        val readOnly: String by bindCollective("", *dataValueArray)

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

    it("readWrite: bind from observable > bind to observable") {
        var data1 = "data1"
        var data2 = "data2"

        val dataValue1 = observable({ data1 = it }, { data1 })
        val dataValue2 = observable({ data2 = it }, { data2 })

        val dataValueArray = arrayOf(dataValue1, dataValue2)
        var readWrite: String by bindCollective("", *dataValueArray)

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
    it("readWrite: bind to observable(at readWrite initialization) > bind from observable") {
        var data1 = "data1"
        var data2 = "data2"

        val dataValue1 = observable({ data1 = it }, { data1 })
        val dataValue2 = observable({ data2 = it }, { data2 })

        val dataValueArray = arrayOf(dataValue1, dataValue2)
        var readWrite: String by bindCollective("", *dataValueArray)

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
