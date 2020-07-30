package com.ataulm.artcollector.gallery.ui

import android.view.View

internal data class UiPainting(
        val id: String,
        val title: String,
        val imageUrl: String,
        val artistId: String,
        val artistName: String,
        /**
         * The `view` parameter is used for the shared element transition.
         */
        val onClickPainting: (View) -> Unit,
        val onClickArtist: () -> Unit
)
