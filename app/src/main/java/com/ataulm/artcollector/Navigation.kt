package com.ataulm.artcollector

import android.content.Intent
import android.net.Uri

enum class Navigation(private val semiQualifiedActivityName: String) {

    GALLERY("gallery.ui.GalleryActivity"),
    PAINTING("painting.ui.PaintingActivity");

    fun viewIntent(artistId: String, paintingId: String): Intent {
        val uri = Uri.Builder()
                .scheme("https://")
                .authority("art-collector.ataulm.com")
                .path("$artistId/$paintingId")
                .build()

        return Intent(Intent.ACTION_VIEW)
                .setClassName(BuildConfig.APPLICATION_ID, "${BuildConfig.APPLICATION_ID}.$semiQualifiedActivityName")
                .setData(uri)
    }
}
