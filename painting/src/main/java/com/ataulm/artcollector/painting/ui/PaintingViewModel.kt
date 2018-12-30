package com.ataulm.artcollector.painting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ataulm.artcollector.domain.Painting
import com.ataulm.artcollector.painting.domain.GetPaintingUseCase
import kotlinx.coroutines.*
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
