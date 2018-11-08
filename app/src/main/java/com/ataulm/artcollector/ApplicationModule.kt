package com.ataulm.artcollector

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object ApplicationModule {

    @JvmStatic
    @Provides
    fun picasso(application: ArtCollectorApplication): Picasso = Picasso.Builder(application).build()

    @JvmStatic
    @Provides
    fun harvardArtMuseumApi(application: ArtCollectorApplication): HarvardArtMuseumApi {
        return Retrofit.Builder()
                .client(OkHttpClient.Builder()
                        .addNetworkInterceptor(AddApiKeyQueryParameterInterceptor(BuildConfig.HARVARD_KEY))
                        .addInterceptor(ChuckInterceptor(application))
                        .build())
                .baseUrl(HarvardArtMuseumApi.ENDPOINT)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(HarvardArtMuseumApi::class.java)
    }
}
