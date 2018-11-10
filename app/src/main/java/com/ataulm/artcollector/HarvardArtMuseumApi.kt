package com.ataulm.artcollector

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HarvardArtMuseumApi {

    @GET("object?$PAINTINGS_&$WITH_IMAGES_&$WITH_ARTIST_&$INC_FIELDS")
    fun paintings(): Deferred<ApiPaintingsResponse>

    @GET("object/{object_id}?$INC_FIELDS")
    fun painting(@Path("object_id") id: String): Deferred<ApiRecord>

    companion object {

        const val ENDPOINT = "https://api.harvardartmuseums.org"
        private const val PAINTINGS_ = "classification=26"
        private const val WITH_IMAGES_ = "hasimage=1"
        private const val WITH_ARTIST_ = "q=people.role:Artist"
        private const val INC_FIELDS = "fields=id,title,description,primaryimageurl,people"
    }
}

internal class AddApiKeyQueryParameterInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
                .addQueryParameter("apikey", apiKey)
                .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

@JsonClass(generateAdapter = true)
data class ApiPaintingsResponse(
        @Json(name = "info") val info: ApiInfo,
        @Json(name = "records") val records: List<ApiRecord>
)

@JsonClass(generateAdapter = true)
data class ApiInfo(
        @Json(name = "totalrecordsperquery") val totalRecordsPerQuery: Int,
        @Json(name = "totalrecords") val totalRecords: Int,
        @Json(name = "pages") val pages: Int,
        @Json(name = "page") val page: Int,
        @Json(name = "next") val next: String
)

@JsonClass(generateAdapter = true)
data class ApiRecord(
        @Json(name = "id") val id: Int,
        @Json(name = "title") val title: String,
        @Json(name = "description") val description: String?,
        @Json(name = "primaryimageurl") val primaryImageUrl: String?,
        @Json(name = "people") val people: List<ApiPerson>
)

@JsonClass(generateAdapter = true)
data class ApiPerson(
        @Json(name = "personid") val personId: Int,
        @Json(name = "name") val name: String,
        @Json(name = "role") val role: String
)
