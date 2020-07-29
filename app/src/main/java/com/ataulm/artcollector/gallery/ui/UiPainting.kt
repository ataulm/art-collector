package com.ataulm.artcollector.gallery.ui

internal data class UiPainting(
        val id: String,
        val title: String,
        val imageUrl: String,
        val artistId: String,
        val artistName: String,
        /**
         * The `Int` parameter is the view adapter position. This is needed to get a reference
         * to the item view for the shared element transition. Same with [onClickArtist].
         */
        val onClickPainting: (Int) -> Unit,
        val onClickArtist: (Int) -> Unit
)
