package com.ataulm.artcollector.gallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ataulm.artcollector.Event
import com.ataulm.artcollector.domain.Artist
import com.ataulm.artcollector.domain.Gallery
import com.ataulm.artcollector.gallery.domain.GetGalleryUseCase
import com.ataulm.artcollector.domain.Painting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GalleryViewModel @Inject constructor(
        private val getGallery: GetGalleryUseCase
) : ViewModel() {

    private val _gallery = MutableLiveData<Gallery>()
    val gallery: LiveData<Gallery> = _gallery

    private val _events = MutableLiveData<Event<NavigateCommand>>()
    val events: LiveData<Event<NavigateCommand>>
        get() = _events

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob)

    init {
        coroutineScope.launch(Dispatchers.IO) {
            val gallery = getGallery()
            withContext(Dispatchers.Main) { _gallery.value = gallery }
        }
    }

    fun onClick(adapterPosition: Int) {
        val painting = _gallery.value!![adapterPosition]
        _events.value = Event(NavigateToPainting(painting, adapterPosition))
    }

    fun onClickArtist(adapterPosition: Int) {
        val artist = _gallery.value!![adapterPosition].artist
        _events.value = Event(NavigateToArtistGallery(artist))
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}

internal sealed class NavigateCommand
internal data class NavigateToPainting(val painting: Painting, val adapterPosition: Int) : NavigateCommand()
internal data class NavigateToArtistGallery(val artist: Artist) : NavigateCommand()
