package com.ataulm.artcollector.gallery.data

import com.ataulm.artcollector.ApiObjectRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.domain.Artist
import com.ataulm.artcollector.domain.Gallery
import com.ataulm.artcollector.domain.Painting
import com.ataulm.artcollector.gallery.domain.GalleryRepository
import javax.inject.Inject

internal class AndroidGalleryRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi
) : GalleryRepository {

    override suspend fun gallery(page: Int): Gallery {
        val apiPaintingsResponse = harvardArtMuseumApi.gallery(page = page).await()
        val totalPages = apiPaintingsResponse.info.pages
        val paintings = apiPaintingsResponse.records
                .map { it.toPainting() }
        return Gallery(totalPages, paintings)
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
}
