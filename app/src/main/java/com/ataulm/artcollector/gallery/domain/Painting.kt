package com.ataulm.artcollector.gallery.domain

import com.ataulm.artcollector.domain.Artist

internal data class Painting(
        val id: String,
        val title: String,
        val description: String?,
        val imageUrl: String,
        val artist: Artist
)
