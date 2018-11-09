package com.ataulm.artcollector.painting.domain

import javax.inject.Inject

internal class GetPaintingUseCase @Inject constructor(
        private val repository: PaintingRepository
) {

    suspend operator fun invoke() = repository.painting()
}
