package com.ataulm.artcollector.artist.data

import com.ataulm.artcollector.ApiObjectRecord
import com.ataulm.artcollector.ApiPerson
import com.ataulm.artcollector.ApiPersonRecord
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

    override suspend fun artist(): Artist {
        val qValue = "personid:${artistId.value}"
        return harvardArtMuseumApi.artist(qValue).await().records.first().toArtist()
    }

    override suspend fun artistGallery(): Gallery {
        val paintings = harvardArtMuseumApi.artistGallery(artistId.value).await().records
                .map { it.toPainting() }
        return Gallery(paintings)
    }

    private fun ApiObjectRecord.toPainting(): Painting {
        return Painting(
                id.toString(),
                title,
                description,
                primaryImageUrl,
                people.first().toArtist()
        )
    }

    private fun ApiPerson.toArtist() = Artist(personId.toString(), name)
    private fun ApiPersonRecord.toArtist() = Artist(personId.toString(), displayName)
}
