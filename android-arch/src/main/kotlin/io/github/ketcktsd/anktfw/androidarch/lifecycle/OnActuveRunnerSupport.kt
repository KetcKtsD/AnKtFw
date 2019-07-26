package io.github.ketcktsd.anktfw.androidarch.lifecycle

interface OnActiveRunnerSupport {
    val onActiveRunner: OnActiveRunner
}

@Suppress("NOTHING_TO_INLINE", "unused")
inline fun OnActiveRunnerSupport.runOnActive(
        noinline handle: () -> Unit
) = onActiveRunner.runOnActive(handle)
