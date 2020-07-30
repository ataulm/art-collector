package com.ataulm.artcollector.datanotdomain

import androidx.paging.PagingSource
import com.ataulm.artcollector.Painting

interface GalleryRepository {

    fun gallery(): PagingSource<Int, Painting>
}
