package com.ataulm.artcollector.gallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ataulm.artcollector.gallery.domain.GetGalleryPageUseCase
import javax.inject.Inject

internal class GalleryViewModelFactory @Inject constructor(
        private val galleryPageUseCase: GetGalleryPageUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            GalleryViewModel(galleryPageUseCase) as T
}
