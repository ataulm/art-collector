package com.ataulm.artcollector

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object ApplicationModule {

    @JvmStatic
    @Provides
    fun glideRequestManager(application: ArtCollectorApplication): RequestManager = Glide.with(application)

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
