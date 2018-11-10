package com.ataulm.artcollector.artist.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ataulm.artcollector.artist.domain.GetArtistGalleryUseCase
import javax.inject.Inject

internal class ArtistViewModelFactory @Inject constructor(
        private val artistGalleryUseCase: GetArtistGalleryUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            ArtistViewModel(artistGalleryUseCase) as T
}
