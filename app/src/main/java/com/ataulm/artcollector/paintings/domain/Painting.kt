package com.ataulm.artcollector.paintings.domain

internal data class Painting(
        val id: String,
        val title: String,
        val description: String?,
        val imageUrl: String
)
