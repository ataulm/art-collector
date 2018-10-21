package com.ataulm.artcollector.paintings

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ataulm.artcollector.HarvardArtMuseumApi
import kotlinx.coroutines.Deferred

internal class PaintingsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val useCase = GetPaintingsUseCase(PaintingsRepository(object:HarvardArtMuseumApi {
            override fun paintings(): Deferred<Any> {
                TODO()
            }
        }))
        @Suppress("UNCHECKED_CAST")
        return PaintingsViewModel(useCase) as T
    }
}
