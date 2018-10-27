package com.ataulm.artcollector.paintings.ui

import android.arch.lifecycle.ViewModel
import com.ataulm.artcollector.paintings.domain.GetPaintingsUseCase
import javax.inject.Inject

internal class PaintingsViewModel @Inject constructor(
        private val getPaintings: GetPaintingsUseCase
) : ViewModel()
