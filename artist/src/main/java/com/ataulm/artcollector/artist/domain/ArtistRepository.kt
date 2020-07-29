package com.ataulm.artcollector.artist.domain

import com.ataulm.artcollector.Artist
import com.ataulm.artcollector.Gallery

internal interface ArtistRepository {

    suspend fun artist(): com.ataulm.artcollector.Artist

    suspend fun artistGallery(): com.ataulm.artcollector.Gallery
}
