package com.ataulm.artcollector.artist.data

import com.ataulm.artcollector.ApiRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.artist.domain.Artist
import com.ataulm.artcollector.artist.domain.ArtistId
import com.ataulm.artcollector.artist.domain.ArtistRepository
import com.ataulm.artcollector.artist.domain.Gallery
import com.ataulm.artcollector.artist.domain.Painting
import javax.inject.Inject

internal class AndroidArtistRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi,
        private val artistId: ArtistId
) : ArtistRepository {

    override suspend fun artistGallery(): Gallery {
        val paintings = harvardArtMuseumApi.artistGallery(artistId.value).await().records
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
