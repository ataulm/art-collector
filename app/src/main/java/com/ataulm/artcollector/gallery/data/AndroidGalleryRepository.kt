package com.ataulm.artcollector.gallery.data

import androidx.paging.PagingSource
import com.ataulm.artcollector.ApiObjectRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.Artist
import com.ataulm.artcollector.Painting
import com.ataulm.artcollector.Gallery
import com.ataulm.artcollector.datanotdomain.GalleryRepository
import javax.inject.Inject

internal class AndroidGalleryRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi
) : GalleryRepository {

    override suspend fun gallery(): Gallery {
        val paintings = harvardArtMuseumApi.gallery().records
                .map { it.toPainting() }
        return Gallery(paintings)
    }

    override fun pagedGallery(): PagingSource<Int, Painting> {
        return GalleryPagingSource(harvardArtMuseumApi)
    }
}

private fun ApiObjectRecord.toPainting(): Painting {
    val apiPerson = people.first()
    return Painting(
            id.toString(),
            title,
            url,
            description,
            creditLine,
            primaryImageUrl,
            Artist(apiPerson.personId.toString(), apiPerson.name)
    )
}

private class GalleryPagingSource(private val harvardArtMuseumApi: HarvardArtMuseumApi)
    : PagingSource<Int, Painting>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Painting> {
        val result = harvardArtMuseumApi.gallery(page = params.key, pageSize = params.loadSize)
        // TODO: handle errors
        return LoadResult.Page(
                data = result.records.map { it.toPainting() },
                prevKey = if (result.info.page == 1) null else result.info.page - 1,
                nextKey = if (result.info.page == result.info.pages) null else result.info.page + 1
        )
    }
}
