package com.ataulm.artcollector.gallery.data

import com.ataulm.artcollector.ApiRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.gallery.domain.Artist
import com.ataulm.artcollector.gallery.domain.Gallery
import com.ataulm.artcollector.gallery.domain.GalleryRepository
import com.ataulm.artcollector.gallery.domain.Painting
import javax.inject.Inject

internal class AndroidGalleryRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi
) : GalleryRepository {

    override suspend fun gallery(): Gallery {
        val paintings = harvardArtMuseumApi.gallery().await().records
                .map { it.toPainting() }
        return Gallery(paintings)
    }

    private fun ApiRecord.toPainting(): Painting {
        val apiPerson = people.first()
        return Painting(
                id.toString(),
                title,
                description,
                primaryImageUrl,
                Artist(apiPerson.personId.toString(), apiPerson.name)
        )
    }
}
