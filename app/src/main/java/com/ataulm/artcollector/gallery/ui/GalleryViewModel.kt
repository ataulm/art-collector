package com.ataulm.artcollector.gallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ataulm.artcollector.Event
import com.ataulm.artcollector.gallery.domain.GetGalleryPageUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val FIRST_PAGE = 1
internal class GalleryViewModel @Inject constructor(
        private val getGalleryPage: GetGalleryPageUseCase
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
            val gallery = getGalleryPage(FIRST_PAGE)
            val paintingUis = gallery.map { painting ->
                UiPainting(
                        painting.id,
                        painting.title,
                        painting.imageUrl,
                        painting.artist.id,
                        painting.artist.name
                )
            }
            val uiGallery = UiGallery(paintingUis)
            withContext(Dispatchers.Main) { _gallery.value = uiGallery }
        }
    }

    fun onClick(adapterPosition: Int) {
        val painting = _gallery.value!![adapterPosition]
        _events.value = Event(NavigateToPainting(painting, adapterPosition))
    }

    fun onClickArtist(adapterPosition: Int) {
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
