package com.ataulm.artcollector.paintings.domain

internal interface PaintingsRepository {

    suspend fun paintings(): List<Painting>
}
