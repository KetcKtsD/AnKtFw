package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.*
import org.jetbrains.spek.api.*
import org.jetbrains.spek.api.dsl.*
import org.junit.platform.runner.*
import org.junit.runner.*
import kotlin.test.*

@RunWith(JUnitPlatform::class)
class RetrySpek : Spek({
    it("excepted 2 retry") {
        runBlocking {
            launch {
                var retryCount = 0
                val result = asyncResult(maxTimes = 1) {
                    retryCount++
                    throw Exception(retryCount.toString())
                }.await()
                assertEquals(2, retryCount)
                assertTrue(result.isFailure)
            }
        }
    }

    it("excepted 1 retry") {
        runBlocking {
            launch {
                var retryCount = 0
                val predicate: (Throwable) -> Boolean = { it is IllegalArgumentException }
                val result = asyncResult(maxTimes = 5, predicate = predicate) {
                    if (retryCount == 1) {
                        throw IllegalStateException()
                    }
                    retryCount++
                    throw IllegalArgumentException()
                }.await()
                assertEquals(1, retryCount)
                assertTrue(result.isFailure)
            }
        }
    }

    it("boundary value check of argument maxTimes") {
        runBlocking {
            launch {
                assertFailsWith(IllegalArgumentException::class) {
                    @Suppress("DeferredResultUnused")
                    asyncResult(maxTimes = -1) { }
                }
                val result = asyncResult(maxTimes = 0) { 1 + 1 }.await()
                assertEquals(2, result.getOrThrow())
            }
        }
    }
})
