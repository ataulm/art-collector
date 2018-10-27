package com.ataulm.artcollector.paintings.domain

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class GetPaintingsUseCase @Inject constructor(
        private val repository: PaintingsRepository
) {

    operator fun invoke() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {

        })
    }
}

