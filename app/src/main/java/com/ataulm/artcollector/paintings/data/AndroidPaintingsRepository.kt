package com.ataulm.artcollector.paintings.data

import com.ataulm.artcollector.ApiPerson
import com.ataulm.artcollector.ApiRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.paintings.domain.Artist
import com.ataulm.artcollector.paintings.domain.Painting
import com.ataulm.artcollector.paintings.domain.PaintingsRepository
import javax.inject.Inject

internal class AndroidPaintingsRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi
) : PaintingsRepository {

    override suspend fun paintings(): List<Painting> {
        return harvardArtMuseumApi.paintings().await()
                .records
                .filter { it.people?.find(whereApiPersonIsAKnownArtist) != null }
                .map { it.toPainting() }
    }

    private fun ApiRecord.toPainting(): Painting {
        val apiPerson = people!!.first(whereApiPersonIsAKnownArtist)
        val artist = Artist(apiPerson.personId.toString(), apiPerson.name)
        return Painting(
                id.toString(),
                title,
                description,
                primaryImageUrl,
                artist
        )
    }

    private val whereApiPersonIsAKnownArtist: (ApiPerson) -> Boolean = {
        it.role == "Artist" && it.name != "Unknown Artist"
    }
}
