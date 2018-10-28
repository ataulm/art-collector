package com.ataulm.artcollector.paintings.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ataulm.artcollector.paintings.domain.Painting
import javax.inject.Inject

internal class PaintingsViewModelFactory @Inject constructor(
        private val paintingsLiveData: LiveData<List<Painting>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            PaintingsViewModel(paintingsLiveData) as T
}
