package com.ataulm.artcollector.artist.domain

import com.ataulm.artcollector.domain.Painting

internal class Gallery(collection: Collection<Painting>) : ArrayList<Painting>(collection)

internal data class ArtistId(val value: String)
