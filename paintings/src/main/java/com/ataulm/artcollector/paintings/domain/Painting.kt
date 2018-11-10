package com.ataulm.artcollector.paintings.domain

internal data class Painting(
        val id: String,
        val title: String,
        val description: String?,
        val imageUrl: String,
        val artist: Artist
)

internal data class Artist(val id: String, val name: String)
