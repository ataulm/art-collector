package com.ataulm.artcollector.paintings.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ataulm.artcollector.paintings.domain.GetPaintingsUseCase
import com.ataulm.artcollector.paintings.domain.Painting
import kotlinx.coroutines.*
import javax.inject.Inject

internal class PaintingsViewModel @Inject constructor(
        val paintings: LiveData<List<Painting>>
) : ViewModel()

internal class PaintingsLiveData @Inject constructor(
        private val getPaintings: GetPaintingsUseCase
) : MutableLiveData<List<Painting>>() {

    private var job: Job? = null

    override fun onActive() {
        super.onActive()
        job = CoroutineScope(Dispatchers.IO).launch {
            val paintings = getPaintings()
            withContext(Dispatchers.Main) { value = paintings }
        }
    }

    override fun onInactive() {
        super.onInactive()
        job?.cancel()
    }
}
