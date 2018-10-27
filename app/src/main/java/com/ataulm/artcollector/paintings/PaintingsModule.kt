package com.ataulm.artcollector.paintings

import android.arch.lifecycle.ViewModelProviders
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.paintings.data.AndroidPaintingsRepository
import com.ataulm.artcollector.paintings.domain.PaintingsRepository
import com.ataulm.artcollector.paintings.ui.PaintingsActivity
import com.ataulm.artcollector.paintings.ui.PaintingsViewModel
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
    fun viewModel(activity: PaintingsActivity, viewModelFactory: PaintingsViewModelFactory) =
            ViewModelProviders.of(activity, viewModelFactory).get(PaintingsViewModel::class.java)

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
