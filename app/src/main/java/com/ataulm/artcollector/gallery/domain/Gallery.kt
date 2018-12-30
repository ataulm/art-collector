package com.ataulm.artcollector.gallery.domain

import com.ataulm.artcollector.domain.Painting

internal class Gallery(collection: Collection<Painting>) : ArrayList<Painting>(collection)
