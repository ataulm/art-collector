package com.ataulm.artcollector

import android.content.Intent
import android.net.Uri

private const val SCHEME = "https"
private const val AUTHORITY = "art-collector.ataulm.com"
private const val GALLERY = "${BuildConfig.APPLICATION_ID}.gallery.ui.GalleryActivity"
private const val ARTIST_GALLERY = "${BuildConfig.APPLICATION_ID}.artist.ui.ArtistActivity"
private const val PAINTING = "${BuildConfig.APPLICATION_ID}.painting.ui.PaintingActivity"

private const val INTENT_EXTRA_IMAGE_URL = "${BuildConfig.APPLICATION_ID}.IMAGE_URL"
fun Intent.imageUrl(): String? = getStringExtra(INTENT_EXTRA_IMAGE_URL)

fun webIntent(webUrl: String): Intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(webUrl))

fun galleryIntent() = intent(GALLERY)

fun artistGalleryIntent(artistId: String): Intent {
    val uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path(artistId)
            .build()

    return intent(ARTIST_GALLERY, uri)
}

fun paintingIntent(artistId: String, paintingId: String, imageUrl: String? = null): Intent {
    val uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path("$artistId/$paintingId")
            .build()

    return intent(PAINTING, uri)
            .putExtra(INTENT_EXTRA_IMAGE_URL, imageUrl)
}

private fun intent(componentName: String, uri: Uri? = null): Intent {
    return Intent(Intent.ACTION_VIEW)
            .setClassName(BuildConfig.APPLICATION_ID, componentName)
            .setData(uri)
}
