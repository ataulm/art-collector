package com.ataulm.artcollector

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun glideRequestManager(): RequestManager

    fun harvardApiMuseumApi(): HarvardArtMuseumApi

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: ArtCollectorApplication): Builder

        fun build(): ApplicationComponent
    }
}

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
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(HarvardArtMuseumApi::class.java)
    }
}
