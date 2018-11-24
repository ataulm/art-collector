package com.ataulm.artcollector

import androidx.lifecycle.Observer

class DataObserver<T>(private val handleData: (T) -> Unit) : Observer<T> {

    override fun onChanged(data: T?) {
        if (data == null) {
            throw IllegalArgumentException("data should never be null")
        }
        handleData(data)
    }
}
