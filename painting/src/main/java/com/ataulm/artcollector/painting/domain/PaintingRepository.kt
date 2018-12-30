package com.ataulm.artcollector.painting.domain

import com.ataulm.artcollector.domain.Painting

internal interface PaintingRepository {

    suspend fun painting(): Painting
}
