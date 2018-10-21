package com.ataulm.artcollector.paintings

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal class GetPaintingsUseCase(
        private val repository: PaintingsRepository
) {

    operator fun invoke() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {

        })
    }
}

