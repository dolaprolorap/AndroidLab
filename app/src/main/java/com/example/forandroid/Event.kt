package com.example.forandroid

class Event<T> {
    private val observers = mutableSetOf<(T) -> Unit>()

    operator fun plusAssign(observer: (T) -> Unit) {
        observers.add(observer)
    }

    operator fun minusAssign(observer: (T) -> Unit) {
        observers.remove(observer)
    }

    operator fun invoke(value: T) {
        for (observer in observers)
            observer(value)
    }
}

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
