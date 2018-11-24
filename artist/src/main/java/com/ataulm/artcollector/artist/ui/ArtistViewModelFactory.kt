package com.ataulm.artcollector.artist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ataulm.artcollector.artist.domain.GetArtistGalleryUseCase
import com.ataulm.artcollector.artist.domain.GetArtistUseCase
import javax.inject.Inject

internal class ArtistViewModelFactory @Inject constructor(
        private val artistUseCase: GetArtistUseCase,
        private val artistGalleryUseCase: GetArtistGalleryUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            ArtistViewModel(artistUseCase, artistGalleryUseCase) as T
}
