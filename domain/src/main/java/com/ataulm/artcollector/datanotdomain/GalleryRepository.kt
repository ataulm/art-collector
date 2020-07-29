package com.ataulm.artcollector.datanotdomain

import com.ataulm.artcollector.Gallery

interface GalleryRepository {

    suspend fun gallery(): Gallery
}
