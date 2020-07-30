package com.ataulm.artcollector.painting.data

import com.ataulm.artcollector.ApiObjectRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.Artist
import com.ataulm.artcollector.Painting
import com.ataulm.artcollector.painting.domain.PaintingId
import com.ataulm.artcollector.painting.domain.PaintingRepository
import javax.inject.Inject

internal class AndroidPaintingRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi,
        private val paintingId: PaintingId
) : PaintingRepository {

    override suspend fun painting(): Painting {
        return harvardArtMuseumApi.painting(paintingId.value).toPainting()
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
