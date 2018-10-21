package com.ataulm.artcollector

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

internal interface HarvardArtMuseumApi {

    @GET("object?$PAINTINGS&$WITH_IMAGES")
    fun paintings(): Deferred<Any>

    companion object {

        const val ENDPOINT = "https://api.harvardartmuseums.org"
        private const val PAINTINGS = "classification=26"
        private const val WITH_IMAGES = "hasimage=1"
    }
}
