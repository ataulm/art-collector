package com.ataulm.artcollector

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HarvardArtMuseumApi {

    @GET("object?$PAINTINGS_&$WITH_IMAGES_&$WITH_ARTIST_AND_ACCESS_TO_IMAGES_&$INC_FIELDS")
    suspend fun gallery(
            @Query("size") pageSize: Int,
            @Query("page") page: Int? = null
    ): ApiPaintingsResponse

    @GET("person")
    suspend fun artist(@Query("q") qValue: String): ApiPersonResponse

    @GET("object?$PAINTINGS_&$WITH_IMAGES_&$INC_FIELDS")
    suspend fun artistGallery(@Query("person") artistId: String): ApiPaintingsResponse

    @GET("object/{object_id}?$INC_FIELDS")
    suspend fun painting(@Path("object_id") id: String): ApiObjectRecord

    companion object {

        const val ENDPOINT = "https://api.harvardartmuseums.org"
        private const val PAINTINGS_ = "classification=26"
        private const val WITH_IMAGES_ = "hasimage=1"
        private const val WITH_ARTIST_AND_ACCESS_TO_IMAGES_ = "q=people.role:Artist AND imagepermissionlevel:0"
        private const val INC_FIELDS = "fields=id,title,description,primaryimageurl,people,url,creditline"
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
        @Json(name = "records") val records: List<ApiObjectRecord>
)

@JsonClass(generateAdapter = true)
data class ApiPersonResponse(
        @Json(name = "info") val info: ApiInfo,
        @Json(name = "records") val records: List<ApiPersonRecord>
)

@JsonClass(generateAdapter = true)
data class ApiInfo(
        @Json(name = "totalrecordsperquery") val totalRecordsPerQuery: Int,
        @Json(name = "totalrecords") val totalRecords: Int,
        @Json(name = "pages") val pages: Int,
        @Json(name = "page") val page: Int
)

@JsonClass(generateAdapter = true)
data class ApiPersonRecord(
        @Json(name = "personid") val personId: Int,
        @Json(name = "displayname") val displayName: String
)

@JsonClass(generateAdapter = true)
data class ApiObjectRecord(
        @Json(name = "id") val id: Int,
        @Json(name = "title") val title: String,
        @Json(name = "description") val description: String?,
        @Json(name = "url") val url: String,
        @Json(name = "creditline") val creditLine: String?,
        @Json(name = "primaryimageurl") val primaryImageUrl: String,
        @Json(name = "people") val people: List<ApiPerson>
)

@JsonClass(generateAdapter = true)
data class ApiPerson(
        @Json(name = "personid") val personId: Int,
        @Json(name = "name") val name: String,
        @Json(name = "role") val role: String
)
