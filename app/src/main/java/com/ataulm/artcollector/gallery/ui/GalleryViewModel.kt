package com.ataulm.artcollector.gallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ataulm.artcollector.Event
import com.ataulm.artcollector.gallery.domain.GetGalleryUseCase
import kotlinx.coroutines.*
import javax.inject.Inject

internal class GalleryViewModel @Inject constructor(
        private val getGallery: GetGalleryUseCase
) : ViewModel() {

    private val _gallery = MutableLiveData<UiGallery>()
    val gallery: LiveData<UiGallery> = _gallery

    private val _events = MutableLiveData<Event<NavigateCommand>>()
    val events: LiveData<Event<NavigateCommand>>
        get() = _events

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob)

    init {
        coroutineScope.launch(Dispatchers.IO) {
            val gallery = getGallery()
            val paintingUis = gallery.map { painting ->
                UiPainting(
                        id = painting.id,
                        title = painting.title,
                        imageUrl = painting.imageUrl,
                        artistId = painting.artist.id,
                        artistName = painting.artist.name,
                        onClickPainting = { onClick(it) },
                        onClickArtist = { onClickArtist(it) }
                )
            }
            val uiGallery = UiGallery(paintingUis)
            withContext(Dispatchers.Main) { _gallery.value = uiGallery }
        }
    }

    private fun onClick(adapterPosition: Int) {
        val painting = _gallery.value!![adapterPosition]
        _events.value = Event(NavigateToPainting(painting, adapterPosition))
    }

    private fun onClickArtist(adapterPosition: Int) {
        val artist = _gallery.value!![adapterPosition].artistId
        _events.value = Event(NavigateToArtistGallery(artist))
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}

internal sealed class NavigateCommand
internal data class NavigateToPainting(val painting: UiPainting, val adapterPosition: Int) : NavigateCommand()
internal data class NavigateToArtistGallery(val artistId: String) : NavigateCommand()
