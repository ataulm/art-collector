package com.ataulm.artcollector.gallery.ui

import androidx.paging.PageKeyedDataSource
import com.ataulm.artcollector.domain.Gallery
import com.ataulm.artcollector.gallery.domain.GetGalleryPageUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val INITIAL_PAGE = 1

internal class GalleryPaintingsDataSource @Inject constructor(
        private val getGalleryPage: GetGalleryPageUseCase
) : PageKeyedDataSource<Int, UiPainting>() {

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, UiPainting>) {
        coroutineScope.launch(Dispatchers.IO) {
            val gallery = getGalleryPage(INITIAL_PAGE)
            val uiPaintings = gallery.toUiPaintings()
            val previousPageKey = null
            val nextPageKey = if (gallery.totalPages == INITIAL_PAGE) null else INITIAL_PAGE + 1

            withContext(Dispatchers.Main) {
                callback.onResult(uiPaintings, previousPageKey, nextPageKey)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UiPainting>) {
        coroutineScope.launch(Dispatchers.IO) {
            val nextPage = params.key
            val gallery = getGalleryPage(nextPage)
            val uiPaintings = gallery.toUiPaintings()
            val nextNextPageKey = if (gallery.totalPages > nextPage) nextPage + 1 else null

            withContext(Dispatchers.Main) {
                callback.onResult(uiPaintings, nextNextPageKey)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UiPainting>) {
        coroutineScope.launch(Dispatchers.IO) {
            val previousPage = params.key
            val gallery = getGalleryPage(previousPage)
            val uiPaintings = gallery.toUiPaintings()
            val previousPreviousPageKey = if (previousPage > INITIAL_PAGE) previousPage - 1 else null

            withContext(Dispatchers.Main) {
                callback.onResult(uiPaintings, previousPreviousPageKey)
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        parentJob.cancelChildren()
    }
}

private fun Gallery.toUiPaintings(): List<UiPainting> {
    return map { painting ->
        UiPainting(
                painting.id,
                painting.title,
                painting.imageUrl,
                painting.artist.id,
                painting.artist.name
        )
    }
}
