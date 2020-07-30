package com.ataulm.artcollector.gallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ataulm.artcollector.gallery.domain.GetGalleryUseCase
import com.ataulm.artcollector.gallery.domain.GetPagedGalleryUseCase
import javax.inject.Inject

internal class GalleryViewModelFactory @Inject constructor(
        private val getGalleryUseCase: GetGalleryUseCase,
        private val getPagedGalleryUseCase: GetPagedGalleryUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            GalleryViewModel(getGalleryUseCase, getPagedGalleryUseCase) as T
}
