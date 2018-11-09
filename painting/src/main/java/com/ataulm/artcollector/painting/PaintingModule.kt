package com.ataulm.artcollector.painting

import android.arch.lifecycle.ViewModelProviders
import com.ataulm.artcollector.painting.data.AndroidPaintingRepository
import com.ataulm.artcollector.painting.domain.PaintingRepository
import com.ataulm.artcollector.painting.ui.PaintingActivity
import com.ataulm.artcollector.painting.ui.PaintingViewModel
import com.ataulm.artcollector.painting.ui.PaintingViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal object PaintingModule {

    @JvmStatic
    @Provides
    fun paintingRepository(paintingRepository: AndroidPaintingRepository): PaintingRepository {
        return paintingRepository
    }

    @JvmStatic
    @Provides
    fun viewModel(activity: PaintingActivity, viewModelFactory: PaintingViewModelFactory) =
            ViewModelProviders.of(activity, viewModelFactory).get(PaintingViewModel::class.java)
}
