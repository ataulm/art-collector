package com.ataulm.artcollector

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ArtCollectorApplication : Application() {

    private lateinit var harvardApi: HarvardApi

    override fun onCreate() {
        super.onCreate()
        harvardApi = Retrofit.Builder()
                .baseUrl(HarvardApi.ENDPOINT)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(HarvardApi::class.java)
    }
}
