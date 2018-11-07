package com.ataulm.artcollector.paintings

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import com.ataulm.artcollector.AddApiKeyQueryParameterInterceptor
import com.ataulm.artcollector.BuildConfig
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.paintings.data.AndroidPaintingsRepository
import com.ataulm.artcollector.paintings.domain.Painting
import com.ataulm.artcollector.paintings.domain.PaintingsRepository
import com.ataulm.artcollector.paintings.ui.PaintingsActivity
import com.ataulm.artcollector.paintings.ui.PaintingsLiveData
import com.ataulm.artcollector.paintings.ui.PaintingsViewModel
import com.ataulm.artcollector.paintings.ui.PaintingsViewModelFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
internal object PaintingsModule {

    @JvmStatic
    @Provides
    fun paintingsRepository(paintingsRepository: AndroidPaintingsRepository): PaintingsRepository {
        return paintingsRepository
    }

    @JvmStatic
    @Provides
    fun paintingsLiveData(paintingsLiveData: PaintingsLiveData): LiveData<List<Painting>> {
        return paintingsLiveData
    }

    @JvmStatic
    @Provides
    fun viewModel(activity: PaintingsActivity, viewModelFactory: PaintingsViewModelFactory) =
            ViewModelProviders.of(activity, viewModelFactory).get(PaintingsViewModel::class.java)

    @JvmStatic
    @Provides
    fun chuckInterceptor(activity: PaintingsActivity) = ChuckInterceptor(activity)

    @JvmStatic
    @Provides
    fun harvardArtMuseumApi(chuckInterceptor: ChuckInterceptor): HarvardArtMuseumApi {
        return Retrofit.Builder()
                .client(OkHttpClient.Builder()
                        .addNetworkInterceptor(AddApiKeyQueryParameterInterceptor(BuildConfig.HARVARD_KEY))
                        .addInterceptor(chuckInterceptor)
                        .build())
                .baseUrl(HarvardArtMuseumApi.ENDPOINT)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(HarvardArtMuseumApi::class.java)
    }
}
