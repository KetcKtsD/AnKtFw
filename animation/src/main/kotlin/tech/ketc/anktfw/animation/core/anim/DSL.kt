package tech.ketc.anktfw.animation.core.anim

import android.view.animation.*


/**
 * create [AlphaAnimation]
 *
 * @param fromAlpha alpha at the start of animation
 * @param toAlpha alpha at the end of animation
 * @param init initialize animation
 */
inline fun alphaAnim(fromAlpha: Float,
                     toAlpha: Float,
                     init: AlphaAnimation.() -> Unit = {}
): AlphaAnimation {

    return AlphaAnimation(fromAlpha, toAlpha).apply(init)
}

/**
 * create [ScaleAnimation]
 *
 * @param fromX horizontal length at the start of animation
 * @param toX horizontal length at the end of animation
 * @param fromY vertical length at the start of animation
 * @param toY vertical length at the end of animation
 * @param init initialize animation()
 */
inline fun scaleAnim(fromX: Float,
                     toX: Float,
                     fromY: Float,
                     toY: Float,
                     init: ScaleAnimation.() -> Unit = {}
): ScaleAnimation {

    return ScaleAnimation(fromX, toX, fromY, toY).apply(init)
}

/**
 * create [ScaleAnimation]
 *
 * @param fromX horizontal length at the start of animation
 * @param toX horizontal length at the end of animation
 * @param fromY vertical length at the start of animation
 * @param toY vertical length at the end of animation
 * @param pivotX center of horizontal animation
 * @param pivotY center of vertical animation
 * @param init initialize animation
 */
inline fun scaleAnim(fromX: Float,
                     toX: Float,
                     fromY: Float,
                     toY: Float,
                     pivotX: Float,
                     pivotY: Float,
                     init: ScaleAnimation.() -> Unit = {}
): ScaleAnimation {

    return ScaleAnimation(fromX, toX, fromY, toY, pivotX, pivotY).apply(init)
}

/**
 * create [ScaleAnimation]
 *
 * @param fromX horizontal length at the start of animation
 * @param toX horizontal length at the end of animation
 * @param fromY vertical length at the start of animation
 * @param toY vertical length at the end of animation
 * @param pivotXType type of [pivotX]
 * @param pivotX center of horizontal animation
 * @param pivotYType type of [pivotY]
 * @param pivotY center of vertical animation
 * @param init initialize animation
 */
inline fun scaleAnim(fromX: Float,
                     toX: Float,
                     fromY: Float,
                     toY: Float,
                     pivotXType: Int,
                     pivotX: Float,
                     pivotYType: Int,
                     pivotY: Float,
                     init: ScaleAnimation.() -> Unit = {}
): ScaleAnimation {

    return ScaleAnimation(fromX, toX, fromY, toY,
            pivotXType, pivotX, pivotYType, pivotY).apply(init)
}

/**
 * create [RotateAnimation]
 *
 * @param fromDegrees degrees at the start of animation
 * @param toDegrees degrees at the end of animation
 * @param init initialize animation
 */
inline fun rotateAnim(fromDegrees: Float,
                      toDegrees: Float,
                      init: RotateAnimation.() -> Unit = {}
): RotateAnimation {

    return RotateAnimation(fromDegrees, toDegrees).apply(init)
}

/**
 * create [RotateAnimation]
 *
 * @param fromDegrees degrees at the start of animation
 * @param toDegrees degrees at the end of animation
 * @param pivotX center of horizontal animation
 * @param pivotY center of vertical animation
 * @param init initialize animation
 */
inline fun rotateAnim(fromDegrees: Float,
                      toDegrees: Float,
                      pivotX: Float,
                      pivotY: Float,
                      init: RotateAnimation.() -> Unit = {}
): RotateAnimation {

    return RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY).apply(init)
}

/**
 * create [RotateAnimation]
 *
 * @param fromDegrees degrees at the start of animation
 * @param toDegrees degrees at the end of animation
 * @param pivotXType type of [pivotX]
 * @param pivotX center of horizontal animation
 * @param pivotYType type of [pivotY]
 * @param pivotY center of vertical animation
 * @param init initialize animation
 */
inline fun rotateAnim(fromDegrees: Float,
                      toDegrees: Float,
                      pivotXType: Int,
                      pivotX: Float,
                      pivotYType: Int,
                      pivotY: Float,
                      init: RotateAnimation.() -> Unit = {}
): RotateAnimation {

    return RotateAnimation(fromDegrees, toDegrees,
            pivotXType, pivotX, pivotYType, pivotY).apply(init)
}

/**
 * create [TranslateAnimation]
 *
 * @param fromXDelta horizontal position at start of animation
 * @param toXDelta horizontal position at end of animation
 * @param fromYDelta vertical position at start of animation
 * @param toYDelta vertical position at end of animation
 * @param init initialize animation
 */
inline fun translateAnim(fromXDelta: Float,
                         toXDelta: Float,
                         fromYDelta: Float,
                         toYDelta: Float,
                         init: TranslateAnimation.() -> Unit = {}
): TranslateAnimation {

    val anim = TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta)
    return anim.apply(init)
}

/**
 * create [TranslateAnimation]
 * @param fromXType type of [fromXDelta]
 * @param fromXDelta horizontal position at start of animation
 * @param toXType type of [toXDelta]
 * @param toXDelta horizontal position at end of animation
 * @param fromYType type of [fromYDelta]
 * @param fromYDelta vertical position at start of animation
 * @param toYType type of [toYDelta]
 * @param toYDelta vertical position at end of animation
 * @param init initialize animation
 */
inline fun translateAnim(fromXType: Int,
                         fromXDelta: Float,
                         toXType: Int,
                         toXDelta: Float,
                         fromYType: Int,
                         fromYDelta: Float,
                         toYType: Int,
                         toYDelta: Float,
                         init: TranslateAnimation.() -> Unit = {}
): TranslateAnimation {

    return TranslateAnimation(fromXType, fromXDelta, toXType, toXDelta,
            fromYType, fromYDelta, toYType, toYDelta).apply(init)
}

/**
 * create [AnimationSet]
 *
 * @param shareInterpolator shareInterpolator
 * @param init initialize animation set
 */
inline fun animSet(shareInterpolator: Boolean,
                   init: AnimationSet.() -> Unit = {}
): AnimationSet {

    return AnimationSet(shareInterpolator).apply(init)
}