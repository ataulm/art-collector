package com.ataulm.artcollector.gallery.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.EventObserver
import com.ataulm.artcollector.R
import com.ataulm.artcollector.artistGalleryIntent
import com.ataulm.artcollector.gallery.domain.Gallery
import com.ataulm.artcollector.gallery.injectDependencies
import com.ataulm.artcollector.paintingIntent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_gallery.*
import javax.inject.Inject

class GalleryActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: PaintingsViewModel

    @Inject
    internal lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val adapter = GalleryAdapter(
                picasso,
                { viewModel.onClick(it) },
                { viewModel.onClickArtist(it) }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this@GalleryActivity, 2)

        viewModel.gallery.observe(this, DataObserver<Gallery> { gallery ->
            adapter.submitList(gallery)
        })

        viewModel.events.observe(this, EventObserver {
            val intent = when (it) {
                is NavigateToArtistGallery -> artistGalleryIntent(it.artist.id)
                is NavigateToPainting -> paintingIntent(it.painting.artist.id, it.painting.id)
            }
            startActivity(intent)
        })
    }
}
