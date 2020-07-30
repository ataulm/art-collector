package com.ataulm.artcollector.gallery.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.ataulm.artcollector.Event
import com.ataulm.artcollector.Painting
import com.ataulm.artcollector.gallery.domain.GetGalleryUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GalleryViewModel @Inject constructor(
        private val getGallery: GetGalleryUseCase
) : ViewModel() {

    private val _events = MutableLiveData<Event<NavigateCommand>>()
    val events: LiveData<Event<NavigateCommand>>
        get() = _events

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob)

    fun pagedGallery(): Flow<PagingData<UiPainting>> {
        val pagingSource = getGallery()
        val pager = Pager(config = PagingConfig(pageSize = 9)) { pagingSource }
        return pager.flow.map { paintingPagingData: PagingData<Painting> ->
            paintingPagingData.map { it.toUiModel() }
            // TODO: we can remove some of this boilerplate
            // https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
        }.cachedIn(coroutineScope)
    }

    private fun Painting.toUiModel() = UiPainting(
            id = id,
            title = title,
            imageUrl = imageUrl,
            artistId = artist.id,
            artistName = artist.name,
            onClickPainting = {
                onClick(
                        artistId = artist.id,
                        paintingId = id,
                        imageUrl = imageUrl,
                        view = it
                )
            },
            onClickArtist = { onClickArtist(artist.id) }
    )

    private fun onClick(
            artistId: String,
            paintingId: String,
            imageUrl: String,
            view: View
    ) {
        _events.value = Event(NavigateToPainting(artistId, paintingId, imageUrl, view))
    }

    private fun onClickArtist(artistId: String) {
        _events.value = Event(NavigateToArtistGallery(artistId))
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}

internal sealed class NavigateCommand
internal data class NavigateToPainting(
        val artistId: String,
        val paintingId: String,
        val imageUrl: String,
        val view: View
) : NavigateCommand()

internal data class NavigateToArtistGallery(val artistId: String) : NavigateCommand()
