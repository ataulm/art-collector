package com.ataulm.artcollector

import android.arch.lifecycle.Observer

class EventObserver<T>(private val handleEvent: (T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        event?.value()?.let { handleEvent(it) }
    }
}

class Event<T>(private val value: T) {

    private var delivered = false

    fun value() = if (delivered) {
        null
    } else {
        delivered = true
        value
    }
}
