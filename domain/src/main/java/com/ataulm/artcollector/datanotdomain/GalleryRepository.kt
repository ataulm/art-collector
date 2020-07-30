package com.ataulm.artcollector.datanotdomain

import androidx.paging.PagingSource
import com.ataulm.artcollector.Gallery
import com.ataulm.artcollector.Painting

interface GalleryRepository {

    suspend fun gallery(): Gallery

    fun pagedGallery(): PagingSource<Int, Painting>
}
