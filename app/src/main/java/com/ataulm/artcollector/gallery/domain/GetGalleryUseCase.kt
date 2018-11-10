package com.ataulm.artcollector.gallery.domain

import javax.inject.Inject

internal class GetGalleryUseCase @Inject constructor(
        private val repository: GalleryRepository
) {

    suspend operator fun invoke() = repository.gallery()
}

