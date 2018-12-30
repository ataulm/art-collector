package com.ataulm.artcollector.artist.domain

import com.ataulm.artcollector.domain.Artist

internal interface ArtistRepository {

    suspend fun artist(): Artist

    suspend fun artistGallery(): Gallery
}
