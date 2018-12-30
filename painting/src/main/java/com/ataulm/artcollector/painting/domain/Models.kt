package com.ataulm.artcollector.painting.domain

import com.ataulm.artcollector.domain.Artist

internal data class Painting(
        val id: String,
        val title: String,
        val webUrl: String,
        val description: String?,
        val creditLine: String?,
        val imageUrl: String,
        val artist: Artist
)

internal data class PaintingId(val value: String)
