package com.ataulm.artcollector.paintings

import android.arch.lifecycle.ViewModelProviders
import com.ataulm.artcollector.paintings.data.AndroidPaintingsRepository
import com.ataulm.artcollector.paintings.domain.PaintingsRepository
import com.ataulm.artcollector.paintings.ui.PaintingsActivity
import com.ataulm.artcollector.paintings.ui.PaintingsViewModel
import com.ataulm.artcollector.paintings.ui.PaintingsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal object PaintingsModule {

    @JvmStatic
    @Provides
    fun paintingsRepository(paintingsRepository: AndroidPaintingsRepository): PaintingsRepository {
        return paintingsRepository
    }

    @JvmStatic
    @Provides
    fun viewModel(activity: PaintingsActivity, viewModelFactory: PaintingsViewModelFactory) =
            ViewModelProviders.of(activity, viewModelFactory).get(PaintingsViewModel::class.java)
}
