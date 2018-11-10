package com.ataulm.artcollector.gallery.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ataulm.artcollector.gallery.domain.GetGalleryUseCase
import javax.inject.Inject

internal class GalleryViewModelFactory @Inject constructor(
        private val galleryUseCase: GetGalleryUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            PaintingsViewModel(galleryUseCase) as T
}
