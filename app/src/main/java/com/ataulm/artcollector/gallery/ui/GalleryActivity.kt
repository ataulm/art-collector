package com.ataulm.artcollector.gallery.ui

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.EventObserver
import com.ataulm.artcollector.R
import com.ataulm.artcollector.artistGalleryIntent
import com.ataulm.artcollector.gallery.domain.Artist
import com.ataulm.artcollector.gallery.domain.Gallery
import com.ataulm.artcollector.gallery.domain.Painting
import com.ataulm.artcollector.gallery.injectDependencies
import com.ataulm.artcollector.paintingIntent
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_gallery.*
import javax.inject.Inject

class GalleryActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: PaintingsViewModel

    @Inject
    internal lateinit var glideRequestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val adapter = GalleryAdapter(
                glideRequestManager,
                onClickPainting(),
                onClickArtist()
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this@GalleryActivity, 2)

        viewModel.gallery.observe(this, DataObserver<Gallery> { gallery ->
            adapter.submitList(gallery)
        })

        viewModel.events.observe(this, EventObserver { command ->
            when (command) {
                is NavigateToArtistGallery -> navigateToArtistGallery(command)
                is NavigateToPainting -> navigateToPainting(command)
            }
        })
    }

    private fun navigateToArtistGallery(it: NavigateToArtistGallery) {
        val intent = artistGalleryIntent(it.artist.id)
        startActivity(intent)
    }

    private fun navigateToPainting(command: NavigateToPainting) {
        val (painting, view) = command.painting to command.view
        val paintingIntent = paintingIntent(painting.artist.id, painting.id, painting.imageUrl)
        val heroImage = Pair(view, getString(R.string.shared_element_painting))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, heroImage)
        startActivity(paintingIntent, options.toBundle())
    }

    private fun onClickArtist(): (Artist) -> Unit = { viewModel.onClickArtist(it) }

    private fun onClickPainting(): (Painting, View) -> Unit {
        return { painting, view ->
            viewModel.onClick(view, painting)
        }
    }
}
