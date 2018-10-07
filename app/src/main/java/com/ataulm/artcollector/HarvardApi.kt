package com.ataulm.artcollector

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

internal interface HarvardApi {

    @GET("object?$QUERY_ONLY_PAINTINGS&$QUERY_ONLY_WITH_IMAGES")
    fun paintings(): Deferred<Any>

    companion object {

        const val ENDPOINT = "https://api.harvardartmuseums.org"
        private const val QUERY_ONLY_PAINTINGS = "classification=26"
        private const val QUERY_ONLY_WITH_IMAGES = "hasimage=1"
    }
}
