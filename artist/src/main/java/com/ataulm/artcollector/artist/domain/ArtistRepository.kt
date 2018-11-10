package com.ataulm.artcollector.artist.domain

internal interface ArtistRepository {

    suspend fun artistGallery(): Gallery
}
