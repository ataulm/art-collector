package com.ataulm.artcollector.painting.domain

import com.ataulm.artcollector.Painting

internal interface PaintingRepository {

    suspend fun painting(): Painting
}
