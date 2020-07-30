package com.ataulm.artcollector.artist.domain

import com.ataulm.artcollector.Artist
import com.ataulm.artcollector.Gallery

internal interface ArtistRepository {

    suspend fun artist(): Artist

    suspend fun artistGallery(): Gallery
}
