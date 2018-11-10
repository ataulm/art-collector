package com.ataulm.artcollector

import android.content.Intent
import android.net.Uri

enum class Navigation(private val semiQualifiedActivityName: String) {

    PAINTING("painting.ui.PaintingActivity"),
    PAINTINGS("paintings.ui.PaintingsActivity");

    fun viewIntent(artistId: String, paintingId: String): Intent {
        val uri = Uri.Builder()
                .scheme("https://")
                .authority("art-collector.ataulm.com")
                .path("$artistId/$paintingId")
                .build()

        return viewIntent().setData(uri)
    }

    fun viewIntent(): Intent {
        val componentName = "${BuildConfig.APPLICATION_ID}.$semiQualifiedActivityName"
        return Intent(Intent.ACTION_VIEW)
                .setClassName(BuildConfig.APPLICATION_ID, componentName)
    }
}
