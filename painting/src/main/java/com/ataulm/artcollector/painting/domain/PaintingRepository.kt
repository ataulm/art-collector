package com.ataulm.artcollector.painting.domain

internal interface PaintingRepository {

    suspend fun painting(): Painting
}
