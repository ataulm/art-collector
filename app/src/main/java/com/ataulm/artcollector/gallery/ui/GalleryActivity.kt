package com.ataulm.artcollector.gallery.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import com.ataulm.artcollector.EventObserver
import com.ataulm.artcollector.R
import com.ataulm.artcollector.artistGalleryIntent
import com.ataulm.artcollector.gallery.injectDependencies
import com.ataulm.artcollector.paintingIntent
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: GalleryViewModel

    @Inject
    internal lateinit var glideRequestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val adapter = GalleryAdapter(glideRequestManager)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GallerySpacingItemDecoration(resources))
        lifecycleScope.launch {
            viewModel.pagedGallery().collectLatest { adapter.submitData(it) }
        }

        viewModel.events.observe(this, EventObserver { command ->
            when (command) {
                is NavigateToArtistGallery -> navigateToArtistGallery(command)
                is NavigateToPainting -> navigateToPainting(command)
            }
        })
    }

    private fun navigateToArtistGallery(it: NavigateToArtistGallery) {
        val intent = artistGalleryIntent(it.artistId)
        startActivity(intent)
    }

    private fun navigateToPainting(command: NavigateToPainting) {
        val paintingIntent = paintingIntent(command.artistId, command.paintingId, command.imageUrl)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(command.view, getString(R.string.shared_element_painting)))
        startActivity(paintingIntent, options.toBundle())
    }
}
