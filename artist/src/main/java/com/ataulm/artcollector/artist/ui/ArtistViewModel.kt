package com.ataulm.artcollector.artist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ataulm.artcollector.Event
import com.ataulm.artcollector.artist.domain.GetArtistGalleryUseCase
import com.ataulm.artcollector.artist.domain.GetArtistUseCase
import com.ataulm.artcollector.Artist
import com.ataulm.artcollector.Gallery
import com.ataulm.artcollector.Painting
import kotlinx.coroutines.*
import javax.inject.Inject

internal class ArtistViewModel @Inject constructor(
        private val getArtist: GetArtistUseCase,
        private val getArtistGallery: GetArtistGalleryUseCase
) : ViewModel() {

    private val _artist = MutableLiveData<com.ataulm.artcollector.Artist>()
    private val _gallery = MutableLiveData<com.ataulm.artcollector.Gallery>()

    private val _artistGallery = ArtistGalleryMediatorLiveData()
    val artistGallery: LiveData<ArtistGallery> = _artistGallery

    private val _events = MutableLiveData<Event<NavigateToPainting>>()
    val events: LiveData<Event<NavigateToPainting>>
        get() = _events

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob)

    init {
        _artistGallery.addSource(_artist) { it?.let { _artistGallery.update(it) } }
        _artistGallery.addSource(_gallery) { it?.let { _artistGallery.update(it) } }

        coroutineScope.launch(Dispatchers.IO) {
            val artist = getArtist()
            withContext(Dispatchers.Main) { _artist.value = artist }
        }

        coroutineScope.launch(Dispatchers.IO) {
            val gallery = getArtistGallery()
            withContext(Dispatchers.Main) { _gallery.value = gallery }
        }
    }

    fun onClick(adapterPosition: Int) {
        val painting = _gallery.value!![adapterPosition]
        _events.value = Event(NavigateToPainting(painting, adapterPosition))
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}

internal data class NavigateToPainting(val painting: com.ataulm.artcollector.Painting, val adapterPosition: Int)

internal data class ArtistGallery(val artist: com.ataulm.artcollector.Artist, val gallery: com.ataulm.artcollector.Gallery)

private class ArtistGalleryMediatorLiveData : MediatorLiveData<ArtistGallery>() {

    private var artist: com.ataulm.artcollector.Artist? = null
    private var gallery: com.ataulm.artcollector.Gallery? = null

    fun update(artist: com.ataulm.artcollector.Artist) {
        this.artist = artist
        onUpdate()
    }

    fun update(gallery: com.ataulm.artcollector.Gallery) {
        this.gallery = gallery
        onUpdate()
    }

    private fun onUpdate() {
        val nonNullArtist = artist ?: return
        val nonNullGallery = gallery ?: return
        value = ArtistGallery(nonNullArtist, nonNullGallery)
    }
}
