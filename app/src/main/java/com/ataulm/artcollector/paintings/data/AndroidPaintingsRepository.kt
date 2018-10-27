package com.ataulm.artcollector.paintings.data

import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.paintings.domain.PaintingsRepository
import javax.inject.Inject

internal class AndroidPaintingsRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi
) : PaintingsRepository {

    override fun paintings() = harvardArtMuseumApi.paintings()
}
