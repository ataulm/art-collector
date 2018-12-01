package com.ataulm.artcollector.gallery.domain

internal class Gallery(collection: Collection<Painting>) : ArrayList<Painting>(collection)

internal data class Painting(
        val id: String,
        val title: String,
        val description: String?,
        val imageUrl: String,
        val artist: Artist
)

internal data class Artist(val id: String, val name: String)
