package com.ataulm.artcollector.artist.domain

internal interface ArtistRepository {

    suspend fun artist(): Artist

    suspend fun artistGallery(): Gallery
}
