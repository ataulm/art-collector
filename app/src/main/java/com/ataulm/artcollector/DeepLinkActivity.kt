package com.ataulm.artcollector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

private const val ARTIST = 0
private const val PAINTING = 1

/**
 * Supports http/https:
 *
 * - art-collector.ataulm.com
 * - art-collector.ataulm.com/{artist-id}
 * - art-collector.ataulm.com/{artist-id}/{painting-id}
 */
class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = getIntentUri()
        val segments = uri.pathSegments
        val intent = when (uri.pathSegments.size) {
            2 -> paintingIntent(segments[ARTIST], segments[PAINTING])
            1 -> artistGalleryIntent(segments[ARTIST])
            else -> galleryIntent()
        }
        startActivity(intent)
        finish()
    }

    private fun getIntentUri() = intent.data!! // only way to open this activity
}
