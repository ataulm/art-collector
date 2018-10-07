package com.ataulm.artcollector

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

internal interface HarvardApi {

    @GET("object?$FILTER_PAINTINGS&$FILTER_HAS_IMAGES")
    fun paintings(): Deferred<Any>

    companion object {

        const val ENDPOINT = "https://api.harvardartmuseums.org"
        private const val FILTER_PAINTINGS = "classification=26"
        private const val FILTER_HAS_IMAGES = "hasimage=1"
    }
}
