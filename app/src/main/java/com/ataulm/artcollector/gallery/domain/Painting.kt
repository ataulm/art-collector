package com.ataulm.artcollector.gallery.domain

internal data class Painting(
        val id: String,
        val title: String,
        val description: String?,
        val imageUrl: String,
        val artist: Artist
)
