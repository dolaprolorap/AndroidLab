package com.example.forandroid.domain.Events

class Event0 {
    private val observers = mutableSetOf<() -> Unit>()

    operator fun plusAssign(observer: () -> Unit) {
        observers.add(observer)
    }

    operator fun minusAssign(observer: () -> Unit) {
        observers.remove(observer)
    }

    operator fun invoke() {
        for (observer in observers)
            observer()
    }
}