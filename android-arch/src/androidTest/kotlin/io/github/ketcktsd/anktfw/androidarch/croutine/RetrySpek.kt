package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.expect

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
                    asyncResult(maxTimes = -1) { }
                }
                val result = asyncResult(maxTimes = 0) { 1 + 1 }.await()
                assertEquals(2, result.getOrThrow())
            }
        }
    }
})
