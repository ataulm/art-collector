package com.ataulm.artcollector.gallery.domain

import com.ataulm.artcollector.domain.Gallery

internal interface GalleryRepository {

    suspend fun gallery(): Gallery
}
