package io.github.ketcktsd.anktfw.androidarch.croutine

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@RunWith(JUnitPlatform::class)
class ResponseSpek : Spek({
    describe("testing generate Success Or Failure") {
        it("expect that Success will return") {
            val result = runOrThrowCatching { 1 + 1 }
            assertTrue { result.isSuccess }
            assertEquals(2, result.getOrThrow())
        }

        it("expect that failure will return") {
            val exception = Exception()
            val result = runOrThrowCatching { throw exception }
            assertTrue { result.isFailure }
            assertEquals(exception, result.exceptionOrNull())
        }

        it("expect thrown IllegalStateException") {
            assertFailsWith<IllegalStateException> {
                runOrThrowCatching(IllegalArgumentException::class) { throw IllegalStateException() }
            }
        }

        it("expect that illegalArgumentException will throw") {
            val illegalArgumentException = IllegalArgumentException()
            val result = runOrThrowCatching(IllegalArgumentException::class) {
                throw illegalArgumentException
            }
            when {
                result.isFailure -> assertEquals(illegalArgumentException, result.exceptionOrNull())
                else -> assert(false) { "it\'s not Failure" }
            }
        }
    }
})
