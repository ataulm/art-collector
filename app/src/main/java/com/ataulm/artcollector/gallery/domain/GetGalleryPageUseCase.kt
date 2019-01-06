package com.ataulm.artcollector.gallery.domain

import javax.inject.Inject

internal class GetGalleryPageUseCase @Inject constructor(
        private val repository: GalleryRepository
) {

    suspend operator fun invoke(page: Int) = repository.gallery(page)
}

