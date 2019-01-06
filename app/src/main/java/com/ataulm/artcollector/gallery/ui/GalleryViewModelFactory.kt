package com.ataulm.artcollector.gallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

internal class GalleryViewModelFactory @Inject constructor(
        private val galleryPaintingsDataSource: GalleryPaintingsDataSource
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            GalleryViewModel(galleryPaintingsDataSource) as T
}
