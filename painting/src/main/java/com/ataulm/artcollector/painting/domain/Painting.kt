package com.ataulm.artcollector.painting.domain

internal data class Painting(
        val id: String,
        val title: String,
        val description: String?,
        val imageUrl: String?, // nullable because https://github.com/harvardartmuseums/api-docs/issues/6
        val artist: Artist
)

internal data class PaintingId(val value: String)

internal data class Artist(val id: String, val name: String)
