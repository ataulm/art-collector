package com.ataulm.artcollector.artist.domain

import com.ataulm.artcollector.domain.Artist

internal class Gallery(collection: Collection<Painting>) : ArrayList<Painting>(collection)

internal data class Painting(
        val id: String,
        val title: String,
        val description: String?,
        val imageUrl: String,
        val artist: Artist
)

internal data class ArtistId(val value: String)
