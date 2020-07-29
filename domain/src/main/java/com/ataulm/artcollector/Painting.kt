package com.ataulm.artcollector

data class Painting(
        val id: String,
        val title: String,
        val webUrl: String,
        val description: String?,
        val creditLine: String?,
        val imageUrl: String,
        val artist: Artist
)
