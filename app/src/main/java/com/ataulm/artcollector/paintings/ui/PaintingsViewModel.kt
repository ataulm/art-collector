package com.ataulm.artcollector.paintings.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ataulm.artcollector.Event
import com.ataulm.artcollector.paintings.domain.GetPaintingsUseCase
import com.ataulm.artcollector.paintings.domain.Painting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PaintingsViewModel @Inject constructor(
        private val getPaintings: GetPaintingsUseCase
) : ViewModel() {

    private val _paintings = MutableLiveData<List<Painting>>()
    val paintings: LiveData<List<Painting>> = _paintings

    private val _events = MutableLiveData<Event<NavigateToPainting>>()
    val events: LiveData<Event<NavigateToPainting>>
        get() = _events

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob)

    init {
        coroutineScope.launch(Dispatchers.IO) {
            val paintings = getPaintings()
            withContext(Dispatchers.Main) { _paintings.value = paintings }
        }
    }

    fun onClick(painting: Painting) {
        _events.value = Event(NavigateToPainting(painting))
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}

internal data class NavigateToPainting(val painting: Painting)
