package com.ataulm.artcollector.painting.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ataulm.artcollector.painting.domain.GetPaintingUseCase
import com.ataulm.artcollector.painting.domain.Painting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PaintingViewModel @Inject constructor(
        private val getPainting: GetPaintingUseCase
) : ViewModel() {

    private val _painting = MutableLiveData<Painting>()
    val painting: LiveData<Painting> = _painting

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob)

    init {
        coroutineScope.launch(Dispatchers.IO) {
            val paintings = getPainting()
            withContext(Dispatchers.Main) { _painting.value = paintings }
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
