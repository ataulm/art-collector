package com.ataulm.artcollector.paintings

import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.paintings.data.AndroidPaintingsRepository
import com.ataulm.artcollector.paintings.domain.GetPaintingsUseCase
import com.ataulm.artcollector.paintings.domain.PaintingsRepository
import com.ataulm.artcollector.paintings.ui.PaintingsViewModelFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
internal object PaintingsModule {

    @Provides
    fun paintingsRepository(paintingsRepository: AndroidPaintingsRepository): PaintingsRepository {
        return paintingsRepository
    }

    @Provides
    fun viewModelFactory(getPaintingsUseCase: GetPaintingsUseCase) =
            PaintingsViewModelFactory(getPaintingsUseCase)

    @Provides
    fun harvardArtMuseumApi(): HarvardArtMuseumApi {
        return Retrofit.Builder()
                .baseUrl(HarvardArtMuseumApi.ENDPOINT)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(HarvardArtMuseumApi::class.java)
    }
}
