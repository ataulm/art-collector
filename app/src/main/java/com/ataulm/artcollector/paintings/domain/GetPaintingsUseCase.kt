package com.ataulm.artcollector.paintings.domain

import javax.inject.Inject

internal class GetPaintingsUseCase @Inject constructor(
        private val repository: PaintingsRepository
) {

    suspend operator fun invoke() = repository.paintings()
}

