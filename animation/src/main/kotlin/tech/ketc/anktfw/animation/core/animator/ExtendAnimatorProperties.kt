package tech.ketc.anktfw.animation.core.animator

import android.view.*

inline var ViewPropertyAnimator.fromAlpha: Float
    set(value) {
        alphaBy(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.toAlpha: Float
    set(value) {
        alpha(value)
    }
    get() {
        throw UnsupportedOperationException()
    }


inline var ViewPropertyAnimator.fromXScale: Float
    set(value) {
        scaleXBy(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.toXScale: Float
    set(value) {
        scaleX(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.fromYScale: Float
    set(value) {
        scaleYBy(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.toYScale: Float
    set(value) {
        scaleY(value)
    }
    get() {
        throw UnsupportedOperationException()
    }


inline var ViewPropertyAnimator.fromDegrees: Float
    set(value) {
        rotationBy(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.toDegrees: Float
    set(value) {
        rotation(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.fromDegreesX: Float
    set(value) {
        rotationXBy(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.toDegreesX: Float
    set(value) {
        rotationX(value)
    }
    get() {
        throw UnsupportedOperationException()
    }


inline var ViewPropertyAnimator.fromDegreesY: Float
    set(value) {
        rotationYBy(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.toDegreesY: Float
    set(value) {
        rotationY(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.fromXDelta: Float
    set(value) {
        translationXBy(value)
    }
    get() {
        throw UnsupportedOperationException()
    }


inline var ViewPropertyAnimator.toXDelta: Float
    set(value) {
        translationX(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.fromYDelta: Float
    set(value) {
        translationYBy(value)
    }
    get() {
        throw UnsupportedOperationException()
    }

inline var ViewPropertyAnimator.toYDelta: Float
    set(value) {
        translationY(value)
    }
    get() {
        throw UnsupportedOperationException()
    }
