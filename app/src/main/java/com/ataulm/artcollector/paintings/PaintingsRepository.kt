package com.ataulm.artcollector.paintings

import com.ataulm.artcollector.HarvardArtMuseumApi

internal class PaintingsRepository(private val harvardArtMuseumApi: HarvardArtMuseumApi) {

    // TODO: convert up into a cleaner data structure
    fun paintings() = harvardArtMuseumApi.paintings()
}
