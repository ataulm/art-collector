package com.ataulm.artcollector.artist.data

import com.ataulm.artcollector.ApiObjectRecord
import com.ataulm.artcollector.ApiPerson
import com.ataulm.artcollector.ApiPersonRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.artist.domain.ArtistId
import com.ataulm.artcollector.artist.domain.ArtistRepository
import com.ataulm.artcollector.Artist
import com.ataulm.artcollector.Gallery
import com.ataulm.artcollector.Painting
import javax.inject.Inject

internal class AndroidArtistRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi,
        private val artistId: ArtistId
) : ArtistRepository {

    override suspend fun artist(): com.ataulm.artcollector.Artist {
        val qValue = "personid:${artistId.value}"
        return harvardArtMuseumApi.artist(qValue).await().records.first().toArtist()
    }

    override suspend fun artistGallery(): com.ataulm.artcollector.Gallery {
        val paintings = harvardArtMuseumApi.artistGallery(artistId.value).await().records
                .map { it.toPainting() }
        return com.ataulm.artcollector.Gallery(paintings)
    }

    private fun ApiObjectRecord.toPainting(): com.ataulm.artcollector.Painting {
        val apiPerson = people.first()
        return com.ataulm.artcollector.Painting(
                id.toString(),
                title,
                url,
                description,
                creditLine,
                primaryImageUrl,
                apiPerson.toArtist()
        )
    }

    private fun ApiPerson.toArtist() = com.ataulm.artcollector.Artist(personId.toString(), name)
    private fun ApiPersonRecord.toArtist() = com.ataulm.artcollector.Artist(personId.toString(), displayName)
}
