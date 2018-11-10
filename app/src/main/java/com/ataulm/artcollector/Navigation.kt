package com.ataulm.artcollector

import android.content.Intent
import android.net.Uri

private const val SCHEME = "https"
private const val AUTHORITY = "art-collector.ataulm.com"
private const val ARTIST_GALLERY = "${BuildConfig.APPLICATION_ID}.artist.ui.ArtistActivity"
private const val PAINTING = "${BuildConfig.APPLICATION_ID}.painting.ui.PaintingActivity"

fun artistGalleryIntent(artistId: String): Intent {
    val uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path(artistId)
            .build()

    return intent(uri, ARTIST_GALLERY)
}

fun paintingIntent(artistId: String, paintingId: String): Intent {
    val uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path("$artistId/$paintingId")
            .build()

    return intent(uri, PAINTING)
}

private fun intent(uri: Uri, componentName: String): Intent {
    return Intent(Intent.ACTION_VIEW)
            .setClassName(BuildConfig.APPLICATION_ID, componentName)
            .setData(uri)
}
