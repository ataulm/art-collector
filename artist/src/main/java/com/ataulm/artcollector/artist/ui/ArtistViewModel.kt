package com.ataulm.artcollector.artist.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ataulm.artcollector.Event
import com.ataulm.artcollector.artist.domain.Artist
import com.ataulm.artcollector.artist.domain.Gallery
import com.ataulm.artcollector.artist.domain.GetArtistGalleryUseCase
import com.ataulm.artcollector.artist.domain.GetArtistUseCase
import com.ataulm.artcollector.artist.domain.Painting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ArtistViewModel @Inject constructor(
        private val getArtist: GetArtistUseCase,
        private val getArtistGallery: GetArtistGalleryUseCase
) : ViewModel() {

    private val _artist = MutableLiveData<Artist>()
    private val _gallery = MutableLiveData<Gallery>()

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

internal data class NavigateToPainting(val painting: Painting, val adapterPosition: Int)

internal data class ArtistGallery(val artist: Artist, val gallery: Gallery)

private class ArtistGalleryMediatorLiveData : MediatorLiveData<ArtistGallery>() {

    private var artist: Artist? = null
    private var gallery: Gallery? = null

    fun update(artist: Artist) {
        this.artist = artist
        onUpdate()
    }

    fun update(gallery: Gallery) {
        this.gallery = gallery
        onUpdate()
    }

    private fun onUpdate() {
        val nonNullArtist = artist ?: return
        val nonNullGallery = gallery ?: return
        value = ArtistGallery(nonNullArtist, nonNullGallery)
    }
}
