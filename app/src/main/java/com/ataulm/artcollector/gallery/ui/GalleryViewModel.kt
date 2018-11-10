package com.ataulm.artcollector.gallery.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ataulm.artcollector.Event
import com.ataulm.artcollector.gallery.domain.Gallery
import com.ataulm.artcollector.gallery.domain.GetGalleryUseCase
import com.ataulm.artcollector.gallery.domain.Painting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PaintingsViewModel @Inject constructor(
        private val getGallery: GetGalleryUseCase
) : ViewModel() {

    private val _gallery = MutableLiveData<Gallery>()
    val gallery: LiveData<Gallery> = _gallery

    private val _events = MutableLiveData<Event<NavigateToPainting>>()
    val events: LiveData<Event<NavigateToPainting>>
        get() = _events

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob)

    init {
        coroutineScope.launch(Dispatchers.IO) {
            val gallery = getGallery()
            withContext(Dispatchers.Main) { _gallery.value = gallery }
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
