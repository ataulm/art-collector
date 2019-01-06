package com.ataulm.artcollector.gallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ataulm.artcollector.Event
import javax.inject.Inject

internal class GalleryViewModel @Inject constructor(
        private val galleryPaintingsDataSource: GalleryPaintingsDataSource
) : ViewModel() {

    private val _gallery = MutableLiveData<UiGallery>()
    val gallery: LiveData<UiGallery> = _gallery

    private val _events = MutableLiveData<Event<NavigateCommand>>()
    val events: LiveData<Event<NavigateCommand>>
        get() = _events

    init {
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build()
        val pagedList = PagedList.Builder(galleryPaintingsDataSource, pagedListConfig)
                // TODO: these need to be explicitly set otherwise the builder throws an exception
//                .setFetchExecutor()
//                .setNotifyExecutor()
                .build()
        _gallery.value = UiGallery(pagedList)
    }

    fun onClick(uiPainting: UiPainting, adapterPosition: Int) {
        _events.value = Event(NavigateToPainting(uiPainting, adapterPosition))
    }

    fun onClickArtist(painting: UiPainting) {
        _events.value = Event(NavigateToArtistGallery(painting.artistId))
    }

    override fun onCleared() {
        super.onCleared()
        //  TODO: is this necessary?
        //  If onCleared is called, will a new VM instance be created? Else this datasource is no good.
        galleryPaintingsDataSource.invalidate()
    }
}

internal sealed class NavigateCommand
internal data class NavigateToPainting(val painting: UiPainting, val adapterPosition: Int) : NavigateCommand()
internal data class NavigateToArtistGallery(val artistId: String) : NavigateCommand()
