package com.ataulm.artcollector

import android.content.Intent

enum class Navigation(private val semiQualifiedActivityName: String) {

    PAINTING("painting.ui.PaintingActivity"),
    PAINTINGS("paintings.ui.PaintingsActivity");

    fun viewIntent(artistId: String, paintingId: String): Intent {
        return viewIntent()
                .putExtra("artistId", artistId)
                .putExtra("paintingId", paintingId)
    }

    fun viewIntent(): Intent {
        val componentName = "${BuildConfig.APPLICATION_ID}.$semiQualifiedActivityName"
        return Intent(Intent.ACTION_VIEW)
                .setClassName(BuildConfig.APPLICATION_ID, componentName)
    }
}
