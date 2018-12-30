package com.ataulm.artcollector.artist.domain

import com.ataulm.artcollector.domain.Artist
import com.ataulm.artcollector.domain.Gallery

internal interface ArtistRepository {

    suspend fun artist(): Artist

    suspend fun artistGallery(): Gallery
}
