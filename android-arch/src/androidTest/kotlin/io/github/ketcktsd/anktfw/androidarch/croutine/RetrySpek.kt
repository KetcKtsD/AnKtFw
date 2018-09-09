package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import kotlin.coroutines.*
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(JUnitPlatform::class)
class RetrySpek : Spek({
    it("excepted 2 retry") {
        runBlocking {
            launch {
                var retryCount = 0
                val result = execAsync(coroutineContext) {
                    retryCount++
                    throw IllegalArgumentException()
                }.retryAwait(2)
                assertEquals(2, retryCount)
                assertTrue(result.isFailure)
            }
        }
    }

    it("excepted 1 retry") {
        runBlocking {
            launch {
                var retryCount = 0
                val result = execAsync(coroutineContext) {
                    if (retryCount == 1) {
                        return@execAsync 1
                    }
                    retryCount++
                    throw IllegalArgumentException()
                }.retryAwait(2) {
                    it is IllegalArgumentException
                }
                assertEquals(1, retryCount)
                assertTrue(result.isFailure)
            }
        }
    }
})
