package com.ataulm.artcollector.gallery.domain

internal interface GalleryRepository {

    suspend fun gallery(): Gallery
}
