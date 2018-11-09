package com.ataulm.artcollector.painting.data

import com.ataulm.artcollector.ApiRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.painting.domain.Artist
import com.ataulm.artcollector.painting.domain.Painting
import com.ataulm.artcollector.painting.domain.PaintingId
import com.ataulm.artcollector.painting.domain.PaintingRepository
import javax.inject.Inject

internal class AndroidPaintingRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi,
        private val paintingId: PaintingId
) : PaintingRepository {

    override suspend fun painting(): Painting {
        return harvardArtMuseumApi.painting(paintingId.value).await().toPainting()
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
