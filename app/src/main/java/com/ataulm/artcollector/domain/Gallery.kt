package com.ataulm.artcollector.domain

class Gallery(
        val totalPages: Int,
        collection: Collection<Painting>
) : ArrayList<Painting>(collection)
