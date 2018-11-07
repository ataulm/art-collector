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
        return harvardArtMuseumApi.paintings().await().records
                .map { apiRecord ->
                    apiRecord to apiRecord.people?.find(whereApiPersonIsAKnownArtist)
                }
                .mapNotNull { it.toPainting() }
    }

    private val whereApiPersonIsAKnownArtist: (ApiPerson) -> Boolean = {
        it.role == "Artist" && it.name != "Unknown Artist"
    }

    private fun Pair<ApiRecord, ApiPerson?>.toPainting(): Painting? {
        val (apiRecord, apiPerson) = this
        return apiPerson?.let {
            Painting(
                    apiRecord.id.toString(),
                    apiRecord.title,
                    apiRecord.description,
                    apiRecord.primaryImageUrl,
                    Artist(it.personId.toString(), it.name)
            )
        }
    }
}
