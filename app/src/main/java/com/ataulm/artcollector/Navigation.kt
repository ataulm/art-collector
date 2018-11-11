package com.ataulm.artcollector

import android.content.Intent
import android.net.Uri

private const val SCHEME = "https"
private const val AUTHORITY = "art-collector.ataulm.com"
private const val GALLERY = "${BuildConfig.APPLICATION_ID}.gallery.ui.GalleryActivity"
private const val ARTIST_GALLERY = "${BuildConfig.APPLICATION_ID}.artist.ui.ArtistActivity"
private const val PAINTING = "${BuildConfig.APPLICATION_ID}.painting.ui.PaintingActivity"

fun galleryIntent() = intent(GALLERY)

fun artistGalleryIntent(artistId: String): Intent {
    val uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path(artistId)
            .build()

    return intent(ARTIST_GALLERY, uri)
}

fun paintingIntent(artistId: String, paintingId: String): Intent {
    val uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path("$artistId/$paintingId")
            .build()

    return intent(PAINTING, uri)
}

private fun intent(componentName: String, uri: Uri? = null): Intent {
    return Intent(Intent.ACTION_VIEW)
            .setClassName(BuildConfig.APPLICATION_ID, componentName)
            .setData(uri)
}
