package com.ataulm.artcollector.gallery.data

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
        val paintings = harvardArtMuseumApi.gallery().await().records
                .map { it.toPainting() }
        return Gallery(paintings)
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
